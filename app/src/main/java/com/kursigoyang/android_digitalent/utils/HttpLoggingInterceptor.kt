package com.kursigoyang.android_digitalent.utils

/*
 *    Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */


import android.content.Context
import okhttp3.*
import okhttp3.internal.http.HttpHeaders
import okhttp3.internal.platform.Platform
import okhttp3.internal.platform.Platform.INFO
import okio.Buffer
import okio.BufferedSink
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 * Created by amitshekhar on 02/05/17.
 */
class HttpLoggingInterceptor(private val logger: Logger = Logger.DEFAULT) : Interceptor {

  enum class Level {
    /**
     * No logs.
     */
    NONE,
    /**
     * Logs request and response lines.
     *
     *
     *
     * Example:
     * <pre>`--> POST /greeting http/1.1 (3-byte body)

     * <-- 200 OK (22ms, 6-byte body)
    `</pre> *
     */
    BASIC,
    /**
     * Logs request and response lines and their respective headers.
     *
     *
     *
     * Example:
     * <pre>`--> POST /greeting http/1.1
     * Host: example.com
     * Content-Type: plain/text
     * Content-Length: 3
     * --> END POST

     * <-- 200 OK (22ms)
     * Content-Type: plain/text
     * Content-Length: 6
     * <-- END HTTP
    `</pre> *
     */
    HEADERS,
    /**
     * Logs request and response lines and their respective headers and bodies (if present).
     *
     *
     *
     * Example:
     * <pre>`--> POST /greeting http/1.1
     * Host: example.com
     * Content-Type: plain/text
     * Content-Length: 3

     * Hi?
     * --> END POST

     * <-- 200 OK (22ms)
     * Content-Type: plain/text
     * Content-Length: 6

     * Hello!
     * <-- END HTTP
    `</pre> *
     */
    BODY
  }

  interface Logger {
    fun log(message: String)

    companion object {

      /**
       * A [Logger] defaults output appropriate for the current platform.
       */
      val DEFAULT: Logger = object : Logger {
        override fun log(message: String) {
          Platform.get().log(Platform.INFO, message, null)
        }
      }
    }
  }

  @Volatile
  private var level = Level.NONE

  /**
   * Change the level at which this interceptor logs.
   */
  fun setLevel(level: Level?): HttpLoggingInterceptor {
    if (level == null) throw NullPointerException("level == null. Use Level.NONE instead.")
    this.level = level
    return this
  }

  fun getLevel(): Level {
    return level
  }

  @Throws(IOException::class)
  override fun intercept(chain: Interceptor.Chain): Response {

    val level = this.level

    val original = chain.request()
    val originalHttpUrl = original.url()

    val url = originalHttpUrl.newBuilder()
      .addQueryParameter("api_key", "9898fbfcdd0748dcf16bace4f0466420")
      .addQueryParameter("language", "en-Us")
      .build()

    // Request customization: add request headers
    val requestBuilder = original.newBuilder().url(url)

    val request = requestBuilder.build()

    val logBody = level == Level.BODY
    val logHeaders = logBody || level == Level.HEADERS

    val requestBody = request.body()
    val hasRequestBody = requestBody != null

    val connection = chain.connection()
    val protocol = if (connection != null) connection.protocol() else Protocol.HTTP_1_1
    var requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol
    if (!logHeaders && hasRequestBody) {
      requestStartMessage += " (" + requestBody?.contentLength() + "-byte body)"
    }
    logger.log(requestStartMessage)

    if (logHeaders) {
      if (hasRequestBody) {
        // Request body headers are only present when installed as a network interceptor. Force
        // them to be included (when available) so there values are known.
        if (requestBody?.contentType() != null) {
          logger.log("Content-Type: " + requestBody?.contentType())
        }
        if (requestBody?.contentLength() != -1L) {
          logger.log("Content-Length: " + requestBody?.contentLength())
        }
      }

      val headers = request.headers()
      var i = 0
      val count = headers.size()
      while (i < count) {
        val name = headers.name(i)
        // Skip headers from the request body as they are explicitly logged above.
        if (!"Content-Type".equals(name, ignoreCase = true) && !"Content-Length".equals(name, ignoreCase = true)) {
          logger.log(name + ": " + headers.value(i))
        }
        i++
      }

      if (!logBody || !hasRequestBody) {
        logger.log("--> END " + request.method())
      } else if (bodyEncoded(request.headers())) {
        logger.log("--> END " + request.method() + " (encoded body omitted)")
      } else {
        val buffer = Buffer()
        requestBody?.writeTo(buffer as BufferedSink)

        var charset = UTF8
        val contentType = requestBody?.contentType()
        if (contentType != null) {
          charset = contentType.charset(UTF8)
        }

        logger.log("")
        if (isPlaintext(buffer)) {
          logger.log(buffer.readString(charset))
          logger.log("--> END " + request.method() + " (" + requestBody?.contentLength() + "-byte body)")
        } else {
          logger.log("--> END " + request.method() + " (binary " + requestBody?.contentLength() + "-byte body omitted)")
        }
      }
    }

    val startNs = System.nanoTime()
    val response: Response
    try {
      response = chain.proceed(request)
    } catch (e: Exception) {
      logger.log("<-- HTTP FAILED: " + e)
      throw e
    }

    val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

    val responseBody = response.body()
    val contentLength = responseBody?.contentLength()
    val bodySize = if (contentLength != -1L) contentLength.toString() + "-byte" else "unknown-length"
    logger.log("<-- " + response.code() + ' ' + response.message() + ' ' + response.request().url() + " (" + tookMs + "ms" + (if (!logHeaders) ", " + bodySize + " body"
    else "") + ')')

    if (logHeaders) {
      val headers = response.headers()
      var i = 0
      val count = headers.size()
      while (i < count) {
        logger.log(headers.name(i) + ": " + headers.value(i))
        i++
      }

      if (!logBody || !HttpHeaders.hasBody(response)) {
        logger.log("<-- END HTTP")
      } else if (bodyEncoded(response.headers())) {
        logger.log("<-- END HTTP (encoded body omitted)")
      } else {
        val source = responseBody?.source()
        source!!.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
        val buffer = source.buffer()

        var charset = UTF8
        val contentType = responseBody.contentType()
        if (contentType != null) {
          charset = contentType.charset(UTF8)
        }

        if (!isPlaintext(buffer)) {
          logger.log("")
          logger.log("<-- END HTTP (binary " + buffer.size() + "-byte body omitted)")
          return response
        }

        if (contentLength != 0L) {
          logger.log("")
          logger.log(buffer.clone().readString(charset))
        }

        logger.log("<-- END HTTP (" + buffer.size() + "-byte body)")
      }
    }

    return response
  }

  private fun bodyEncoded(headers: Headers): Boolean {
    val contentEncoding = headers.get("Content-Encoding")
    return contentEncoding != null && !contentEncoding.equals("identity", ignoreCase = true)
  }

  companion object {

    val UTF8 = Charset.forName("UTF-8")

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    internal fun isPlaintext(buffer: Buffer): Boolean {
      try {
        val prefix = Buffer()
        val byteCount = if (buffer.size() < 64) buffer.size() else 64
        buffer.copyTo(prefix, 0, byteCount)
        for (i in 0..15) {
          if (prefix.exhausted()) {
            break
          }
          val codePoint = prefix.readUtf8CodePoint()
          if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
            return false
          }
        }
        return true
      } catch (e: EOFException) {
        return false // Truncated UTF-8 sequence.
      }

    }
  }
}