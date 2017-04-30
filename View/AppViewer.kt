package View

import Controller.AppController
import Controller.ControllerInterface
import Model.Bmp24Model
import Model.Bmp32Model
import Model.ModelInterface
import Painters.Bmp24Painter
import Painters.Bmp32Painter
import Painters.Painter
import java.awt.event.ActionListener
import com.sun.java.accessibility.util.AWTEventMonitor.addActionListener
import java.awt.*
import java.awt.event.ActionEvent
import javax.swing.*
import javax.swing.Box.createVerticalBox
import javax.swing.BoxLayout
import javax.swing.border.EmptyBorder


class AppViewer : ViewInterface {

    override var model: ModelInterface? = null
    override var controller: ControllerInterface? = null
    override var painter: Painter? = null
    override var frame = JFrame("FileViewer")

    init {
        frame.setBounds(50, 50, 1000, 700)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.layout = GridBagLayout()

        var menuBar = JMenuBar()
        var menuFile = JMenu("File")

        var openFileItem = JMenuItem("Open...")
        menuFile.add(openFileItem)
        openFileItem.addActionListener(object : ActionListener{
            override fun actionPerformed(e: ActionEvent?) {
                var fileChooser = JFileChooser()
                fileChooser.showDialog(frame, "open")
                var file = fileChooser.selectedFile
                if (file != null)
                    read(file.absolutePath)
            }

        })

        menuBar.add(menuFile)

        frame.jMenuBar = menuBar
        frame.isVisible = true
        controller = AppController(this)
    }

    override fun repaint() {
        frame.repaint()
    }

    fun read(file: String) {
        controller!!.setData(file)
    }

    override fun update(model: ModelInterface) {
        if (painter != null)
            frame.remove(painter)
        when (model) {
            is Bmp24Model ->
                    painter = Bmp24Painter(model)

            is Bmp32Model ->
                    painter = Bmp32Painter(model)

        }

        frame.add(painter)
        frame.isVisible = true
        //this.repaint()
    }
}