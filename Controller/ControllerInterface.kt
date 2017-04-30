package Controller

import Model.ModelInterface
import View.ViewInterface

interface ControllerInterface {

    var model : ModelInterface
    var view : ViewInterface

    fun setData(file : String)

    fun setModel(data: ByteArray)
}