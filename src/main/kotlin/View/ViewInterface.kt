package View

import Controller.ControllerInterface
import Model.ModelInterface
import View.Painters.Painter
import javax.swing.JFrame
import javax.swing.JOptionPane


abstract class ViewInterface(title : String?) : JFrame(title), Observer{
    abstract var model : ModelInterface?
    abstract var controller : ControllerInterface?
    abstract var painter : Painter?

    fun showDialogMessage(message : String){
        JOptionPane.showMessageDialog(this, message)
    }
}