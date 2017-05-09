package View.Painters

import Model.ModelInterface
import java.awt.Color
import java.awt.Graphics

/**
 * Created by Вдадимир on 30.04.2017.
 */
class Bmp32Painter(model: ModelInterface) : Painter(model){

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)

        if (g == null) {
            return
        }

        var ind = model.pixelArray!!.size - 1
        println ("Frame repainted BMP32")
        var shift = (ind + 1) / model.height - 4 * model.width
        println ("shift = $shift")
        for (i in 0..model.height - 1) {
            ind -= shift

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