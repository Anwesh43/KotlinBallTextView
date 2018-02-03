package ui.anwesome.com.balltextview

import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * Created by anweshmishra on 03/02/18.
 */
class BallTextAnimator {
    var thread: Thread? = null
    var runner = BallTextRunner(this)
    var animations: ConcurrentLinkedQueue<BallTextAnimation> = ConcurrentLinkedQueue()
    fun add(animation: BallTextAnimation) {
        animations.add(animation)
        start()
    }
    fun start() {
        if (animations.size == 1) {
            runner.start {
                thread = Thread(runner)
                thread?.start()
            }
        }
    }
    fun execute() {
        animations.forEach {
            if(it.stopCondition()) {
                animations.remove(it)
                if(animations.size == 0) {
                    runner.stop {
                        thread = null
                    }
                }
            }
            it.view.postInvalidate()
        }
    }

    fun pause() {
        runner.pause {
            while(true) {
                try {
                    thread?.join()
                    thread = null
                    break
                }
                catch (ex:Exception) {

                }
            }
        }
    }

    fun resume() {
        start()
    }

    class BallTextRunner(var animator: BallTextAnimator) : Runnable {
        var running = false
        override fun run() {
            while(running) {
                try {
                    Thread.sleep(50)
                    animator.execute()

                } catch(ex: Exception) {

                }
            }
        }

        fun pause(cb: () -> Unit) {
            stop(cb)
        }

        fun start(cb: () -> Unit) {
            if (!running) {
                running = true
                cb()
            }
        }

        fun stop(cb: () -> Unit) {
            if (running) {
                running = false
                cb()
            }
        }

        fun resume(cb: () -> Unit) {
            start(cb)
        }
    }
}