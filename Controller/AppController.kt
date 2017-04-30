package Controller

import Model.Bmp24Model
import Model.Bmp32Model
import Model.ModelInterface
import View.ViewInterface
import java.io.FileInputStream
import java.io.IOException

class AppController (override var view: ViewInterface,
                     override var model : ModelInterface = Bmp24Model()) : ControllerInterface{

    override fun setData(file : String){
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
        if (type == "BM")
            type += data[0x1C]
        print(type)
        when (type) {
            "BM24" -> {
                    if (model !is Bmp24Model)
                        model = Bmp24Model()
            }
            "BM32" -> {
                if (model !is Bmp32Model)
                    model = Bmp32Model()
            }
        }
        model.registerObserver(view)
        model.inflate(data)
    }
}