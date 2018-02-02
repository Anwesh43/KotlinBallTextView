package ui.anwesome.com.balltextview

/**
 * Created by anweshmishra on 03/02/18.
 */
import android.content.*
import android.graphics.*
import android.view.*
class BallTextView(ctx:Context):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas:Canvas) {

    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}