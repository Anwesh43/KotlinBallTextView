package ui.anwesome.com.balltextview

/**
 * Created by anweshmishra on 03/02/18.
 */
import android.app.Activity
import android.content.*
import android.graphics.*
import android.view.*
class BallTextView(ctx:Context,var text:String,var color:Int = Color.parseColor("#ef5350")):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val renderer = Renderer(this)
    var ballTextExpandListener:BallTextExpandListener?=null
    var animator:BallTextAnimator?=null
    var ballTextAnimation = BallTextAnimation.create(this,{
        !renderer.animator.animated
    })
    fun addBallTextExpandListener(onExpandListener: ()->Unit, onCollapseListener: ()->Unit) {
        ballTextExpandListener = BallTextExpandListener(onExpandListener, onCollapseListener)
    }
    override fun onDraw(canvas:Canvas) {
        renderer.render(canvas,paint)
    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap {
                    animator?.add(ballTextAnimation)
                }
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
                scales[j] = prevScale + dir
                j+=jDir
                if(j == scales.size || j == -1) {
                    jDir *= -1
                    j += jDir
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
    data class Animator(var view:BallTextView, var animated:Boolean = false) {
        fun animate(updatecb:()->Unit) {
            if(animated) {
                updatecb()
            }
        }
        fun start() {
            if(!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop() {
            if(animated) {
                animated = false
            }
        }
    }
    data class Renderer(var view:BallTextView, var time:Int = 0) {
        var ballText:BallText?=null
        val animator = Animator(view)
        fun render(canvas:Canvas, paint:Paint) {
            if (time == 0) {
                val w = canvas.width.toFloat()
                val h = canvas.height.toFloat()
                val size = Math.min(w, h)
                ballText = BallText(size, view.text, view.color)
            }
            canvas.drawColor(Color.parseColor("#00000000"))
            ballText?.draw(canvas, paint)
            time++
            animator.animate {
                ballText?.update {
                    animator.stop()
                    when(it) {
                        0f -> view.ballTextExpandListener?.onCollapseListener?.invoke()
                        1f -> view.ballTextExpandListener?.onExpandListener?.invoke()
                    }
                }
            }
        }
        fun handleTap(cb :()->Unit) {
            ballText?.startUpdating {
                animator.start()
                cb()
            }
        }
    }
    data class BallTextExpandListener(var onExpandListener:()->Unit,var onCollapseListener:()->Unit)
    companion object {
        var x = 0f
        var y = 0f
        var ballTextAnimator = BallTextAnimator()
        fun create(activity:Activity,letter:Char,vararg colors:Int):BallTextView {
            val view = BallTextView(activity,"$letter")
            view.animator = ballTextAnimator
            if(colors.size == 1) {
                view.color = colors[0]
            }
            val size = DimensionUtil.getSize(activity)
            view.x = x
            view.y = y
            x += size.x/3
            if(x >= size.x)  {
                x = 0f
                y += size.x/3
            }
            view.animator = ballTextAnimator
            activity.addContentView(view,ViewGroup.LayoutParams(size.x/3,size.x/3))
            return view
        }
        fun pause() {
            ballTextAnimator.pause()
        }
        fun resume() {
            ballTextAnimator.resume()
        }
    }
}