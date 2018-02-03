package ui.anwesome.com.balltextview

import android.view.View

/**
 * Created by anweshmishra on 03/02/18.
 */
data class BallTextAnimation(var view:View,var i:Int,var stopCondition:()->Boolean) {
    companion object {
        var i = 0
        fun create(view:View,stopCondition:()->Boolean):BallTextAnimation {
            return BallTextAnimation(view,i++,stopCondition)
        }
    }
}