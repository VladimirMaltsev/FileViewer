package Controller

import Model.Bmp24Model
import Model.Bmp32Model
import Model.Bmp8Model
import Model.ModelInterface
import View.ViewInterface
import java.io.FileInputStream
import java.io.IOException

class AppController (override var view: ViewInterface,
                     override var model : ModelInterface = Bmp24Model()) : ControllerInterface{

    override fun readFile(file : String){

        var streamIn : FileInputStream

        try { streamIn = FileInputStream(file) }
        catch (e : IOException) {
            println("file not found")
            return
        }

        var data : ByteArray = ByteArray(streamIn.available())
        streamIn.read(data)
        setModel(data)
    }

    override fun setModel(data: ByteArray) {
        var type : String = String(data.copyOfRange(0, 2))
        print(type)

        when (type) {

            "BM" -> {
                when (data[0x1C].toInt()) {

                    8 ->
                        if (model !is Bmp8Model)
                            model = Bmp8Model()

                    24 ->
                        if (model !is Bmp24Model)
                            model = Bmp24Model()

                    32 ->
                        if (model !is Bmp32Model)
                            model = Bmp32Model()

                    else -> {
                        println("unsupported format")
                        return
                    }
                }
            }
        }

        model.registerObserver(view)
        model.inflate(data)
    }
}