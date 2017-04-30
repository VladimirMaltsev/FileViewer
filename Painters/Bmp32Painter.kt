package Painters

import Model.Bmp24Model
import Model.Bmp32Model
import java.awt.Color
import java.awt.Graphics

/**
 * Created by Вдадимир on 30.04.2017.
 */
class Bmp32Painter(model: Bmp32Model) : Painter(model){

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)

        if (g == null) {
            return
        }

        var ind = model.pixelArray!!.size - 1
        println ("Frame repainted BMP32")

        for (i in 0..model.height - 1) {
            for (j in model.width - 1 downTo 0) {
                var alpha = model.pixelArray!![ind --].toInt()
                if (alpha < 0)
                    alpha += 256

                var red = model.pixelArray!![ind --].toInt()
                if (red < 0)
                    red += 256

                var green = model.pixelArray!![ind --].toInt()
                if (green < 0)
                    green += 256

                var blue = model.pixelArray!! [ind --].toInt()
                if (blue < 0)
                    blue += 256
                //println ("${image[ind--].toInt() + 128}, ${image[ind--].toInt() + 128}, ${image[ind--].toInt() + 128}")
                g.color = Color(red, green, blue, alpha)

                g.drawLine(j, i, j, i)
            }
        }
    }

}