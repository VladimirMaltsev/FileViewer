import Model.Bmp24Model
import Model.Bmp32Model
import Model.Bmp8Model
import org.junit.jupiter.api.Assertions.*
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import javax.imageio.ImageIO

/**
 * Created by Вдадимир on 09.05.2017.
 * @link github.com/MaltsevVladimir
 * spbu, 2017
 */
internal class AppTests {

    fun getData(file_name : String) : ByteArray? {
        val streamIn : FileInputStream
        try {
            streamIn = FileInputStream(file_name)
        }
        catch (e : IOException) {
            println("file not found")
            return null
        }
        val data : ByteArray = ByteArray(streamIn.available())
        streamIn.read(data)
        return data
    }

    @org.junit.jupiter.api.Test
    fun isBmp8ImageRight() {
        val file_name = "src/test/kotlin/freebsd2_8bit.bmp"
        var bufImage : BufferedImage? = null
        try{
            bufImage = ImageIO.read(File (file_name))
        }catch (e : IOException){
            println ("file does not exist")
        }

        val bmp8model = Bmp8Model()
        bmp8model.inflate(getData(file_name)!!)

        var ind = bmp8model.pixelArray!!.size - 1

        val shift = (ind + 1) / bmp8model.height - bmp8model.width

        for (i in 0..bmp8model.height - 1) {
            ind -= shift
            for (j in bmp8model.width - 1 downTo 0) {

                var col = bmp8model.pixelArray!![ind --].toInt()

                if (col < 0) {
                    col += 256
                }
                col *= 4
                var blue = bmp8model.colorTable!![col++].toInt()
                if (blue < 0)
                    blue += 256

                var green = bmp8model.colorTable!![col++].toInt()
                if (green < 0)
                    green += 256

                var red = bmp8model.colorTable!![col].toInt()
                if (red < 0)
                    red += 256

                val color = Color(red, green, blue)
                if (Color (bufImage!!.getRGB(j, i)) != color){
                    println(color)
                    println (Color(bufImage.getRGB(j, i)))
                    assertEquals(2, 1)
                }
            }
        }
    }

    @org.junit.jupiter.api.Test
    fun isBmp24ImageRight() {
        val file_name = "src/test/kotlin/love_24bit.bmp"
        var bufImage: BufferedImage? = null
        try {
            bufImage = ImageIO.read(File(file_name))
        } catch (e: IOException) {
            println("file does not exist")
        }

        val bmp24model = Bmp24Model()
        bmp24model.inflate(getData(file_name)!!)

        var ind = bmp24model.pixelArray!!.size - 1
        val shift = (ind + 1) / bmp24model.height - 3 * bmp24model.width

        for (i in 0..bmp24model.height - 1) {

            ind -= shift

            for (j in bmp24model.width - 1 downTo 0) {

                var red = bmp24model.pixelArray!![ind--].toInt()
                if (red < 0)
                    red += 256

                var green = bmp24model.pixelArray!![ind--].toInt()
                if (green < 0)
                    green += 256

                var blue = bmp24model.pixelArray!! [ind--].toInt()
                if (blue < 0)
                    blue += 256

                var color = Color(red, green, blue)
                if (Color(bufImage!!.getRGB(j, i)) != color) {
                    println(color)
                    println(Color(bufImage.getRGB(j, i)))
                    assertEquals(2, 1)
                }
            }

        }
    }

    @org.junit.jupiter.api.Test
    fun isBmp32ImageRight() {
        val file_name = "src/test/kotlin/pahom_32bit.bmp"
        var bufImage: BufferedImage? = null
        try {
            bufImage = ImageIO.read(File(file_name))
        } catch (e: IOException) {
            println("file does not exist")
        }

        val bmp32Model = Bmp32Model()
        bmp32Model.inflate(getData(file_name)!!)

        var ind = bmp32Model.pixelArray!!.size - 1
        val shift = (ind + 1) / bmp32Model.height - 4 * bmp32Model.width

        for (i in 0..bmp32Model.height - 1) {
            ind -= shift

            for (j in bmp32Model.width - 1 downTo 0) {
                var alpha = bmp32Model.pixelArray!![ind --].toInt()
                if (alpha < 0)
                    alpha += 256

                var red = bmp32Model.pixelArray!![ind --].toInt()
                if (red < 0)
                    red += 256

                var green = bmp32Model.pixelArray!![ind --].toInt()
                if (green < 0)
                    green += 256

                var blue = bmp32Model.pixelArray!! [ind --].toInt()
                if (blue < 0)
                    blue += 256

                var color = Color(red, green, blue)
                if (Color(bufImage!!.getRGB(j, i)) != color) {
                    println(color)
                    println(Color(bufImage.getRGB(j, i)))
                    assertEquals(2, 1)
                }
            }

        }
    }
}