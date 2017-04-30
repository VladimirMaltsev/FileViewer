package View

import Controller.ControllerInterface
import Model.ModelInterface
import Painters.Painter
import javax.swing.JFrame


interface ViewInterface : Observer{
    var model : ModelInterface?
    var controller : ControllerInterface?
    var painter : Painter?
    var frame : JFrame


    fun repaint()
}