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
        try {
            streamIn = FileInputStream(file)
        }
        catch (e : IOException) {
            println("file not found")
            return
        }

        var data : ByteArray = ByteArray(streamIn.available())
        streamIn.read(data)

        var file_extension = file.split(".")[1].subSequence(0, 1)
        println (file_extension)

        if (file_extension != String(data.copyOfRange(0, 1))) {
            view.showDialogMessage("Некорректное расширение файла")
            println("Некорректное расширение файла")
            return
        }

        setModel(data)
    }

    override fun setModel(data: ByteArray) {
        var type : String = String(data.copyOfRange(0, 2))

        when (type)
        {
            "BM" ->
            {
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

                }
            }
            else ->
            {
                view.showDialogMessage("unsupported format")
                println("unsupported format")
                return
            }
        }

        model.registerObserver(view)
        model.inflate(data)
    }
}