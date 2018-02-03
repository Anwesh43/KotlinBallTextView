package ui.anwesome.com.kotlinballtextview

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ui.anwesome.com.balltextview.BallTextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BallTextView.create(this,'1')
        BallTextView.create(this,'2',Color.parseColor("#673AB7"))
        BallTextView.create(this,'3',Color.parseColor("#00897B"))
        val view = BallTextView.create(this,'4',Color.parseColor("#F4511E"))
        view.addBallTextExpandListener({
            Toast.makeText(this,"expanded",Toast.LENGTH_SHORT).show()
        },{
            Toast.makeText(this,"collapsed",Toast.LENGTH_SHORT).show()
        })
    }
    override fun onPause() {
        super.onPause()
        BallTextView.pause()
    }
    override fun onResume() {
        super.onResume()
        BallTextView.resume()
    }
}
