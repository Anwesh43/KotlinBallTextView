package ui.anwesome.com.kotlinballtextview

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.balltextview.BallTextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BallTextView.create(this,'1')
        BallTextView.create(this,'2',Color.parseColor("#673AB7"))
        BallTextView.create(this,'3',Color.parseColor("#00897B"))
        BallTextView.create(this,'4',Color.parseColor("#F4511E"))
    }
}
