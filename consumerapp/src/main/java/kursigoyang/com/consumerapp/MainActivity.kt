package kursigoyang.com.consumerapp

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val cursor = contentResolver.query(DatabaseContract.MovieColumns.CONTENT_URI, arrayOf(DatabaseContract.MovieColumns.ID, DatabaseContract.MovieColumns.TITLE), null, null, null, null)

    val lyLayout = LinearLayout(this)
    lyLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
    lyLayout.orientation = LinearLayout.VERTICAL

    if (cursor != null && cursor.count > 0) {
      while (cursor.moveToNext()) {
        val txtTitle = TextView(this)
        txtTitle.setPadding(16, 16, 16, 16)
        txtTitle.text = cursor.getString(cursor.getColumnIndex(DatabaseContract.MovieColumns.TITLE))
        lyLayout.addView(txtTitle)
      }

    }
    lyParent.addView(lyLayout)

    cursor?.close()

  }
}
