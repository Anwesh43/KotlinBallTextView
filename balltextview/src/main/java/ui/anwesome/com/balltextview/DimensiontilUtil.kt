package ui.anwesome.com.balltextview

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.hardware.display.DisplayManager

/**
 * Created by anweshmishra on 03/02/18.
 */
class DimensionUtil {
    companion object {
        fun getSize(activity:Activity):Point {
            val displayManager = activity.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
            val display = displayManager.getDisplay(0)
            val size = Point()
            display?.getRealSize(size)
            return size
        }
    }
}