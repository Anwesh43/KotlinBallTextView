package ui.anwesome.com.balltextview

/**
 * Created by anweshmishra on 03/02/18.
 */
import android.content.*
import android.graphics.*
import android.view.*
class BallTextView(ctx:Context,var text:String,var color:Int = Color.parseColor("#ef5350")):View(ctx) {
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
    data class BallText(var size:Float,var text:String,var color:Int) {
        fun draw(canvas:Canvas,paint:Paint) {
            canvas.save()
            canvas.translate(size/2,size/2)
            val scale = 0.1f+0.9f
            canvas.scale(scale,scale)
            paint.color = color
            canvas.drawCircle(0f,0f,size/2,paint)
            paint.textSize = size/3
            paint.color = Color.WHITE
            canvas.save()
            canvas.rotate(360f)
            canvas.scale(1f,1f)
            canvas.drawText(text,-paint.measureText(text)/2,size/12,paint)
            canvas.restore()
            canvas.restore()
        }
        fun update(stopcb:(Float)->Unit) {

        }
        fun startUpdating(startcb:()->Unit) {

        }
    }
}