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
        val state = BallTextState()
        fun draw(canvas:Canvas,paint:Paint) {
            canvas.save()
            canvas.translate(size/2,size/2)
            state.executeForScale({
                val scale = 0.1f+0.9f*it
                canvas.scale(scale,scale)
            },0)
            paint.color = color
            canvas.drawCircle(0f,0f,size/2,paint)
            paint.textSize = size/3
            paint.color = Color.WHITE
            canvas.save()
            state.executeForScale({
                canvas.rotate(360f*it)
                canvas.scale(it,it)
            },1)
            canvas.drawText(text,-paint.measureText(text)/2,size/9,paint)
            canvas.restore()
            canvas.restore()
        }
        fun update(stopcb:(Float)->Unit) {
            state.update(stopcb)
        }
        fun startUpdating(startcb:()->Unit) {
            state.startUpdating(startcb)
        }
    }
    data class BallTextState(var dir:Float = 0f,var jDir:Int = 1,var j:Int = 0,var prevScale:Float = 0f) {
        val scales:Array<Float> = arrayOf(0f,0f)
        fun update(stopcb:(Float)->Unit) {
            scales[j] += 0.1f*dir
            if(Math.abs(scales[j] - prevScale) > 1) {
                j+=jDir
                scales[j] = prevScale + dir
                if(j == scales.size || j == -1) {
                    jDir *= -1
                    dir = 0f
                    prevScale = scales[j]
                    stopcb(prevScale)
                }
            }
        }
        fun startUpdating(startcb:()->Unit) {
            if(dir == 0f) {
                dir = 1-2*prevScale
                startcb()
            }
        }
        fun executeForScale(cb:(Float)->Unit, n:Int) {
            cb(scales[n])
        }
    }
}