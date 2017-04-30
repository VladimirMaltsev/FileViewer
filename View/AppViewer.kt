package View

import Controller.AppController
import Controller.ControllerInterface
import Model.Bmp24Model
import Model.Bmp32Model
import Model.Bmp8Model
import Model.ModelInterface
import Painters.Bmp24Painter
import Painters.Bmp32Painter
import Painters.Bmp8Painter
import Painters.Painter
import java.awt.event.ActionListener
import com.sun.java.accessibility.util.AWTEventMonitor.addActionListener
import java.awt.*
import java.awt.event.ActionEvent
import javax.swing.*
import javax.swing.Box.createVerticalBox
import javax.swing.BoxLayout
import javax.swing.border.EmptyBorder
import java.awt.GridBagConstraints




class AppViewer(title: String?) : ViewInterface, JFrame(title) {

    override var model: ModelInterface? = null
    override var controller: ControllerInterface? = null
    override var painter: Painter? = null

    init {
        //задаем размеры нашему окну
        setBounds(50, 50, 1000, 700)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        contentPane.layout = GridBagLayout()

        var menuBar = JMenuBar()
        var menuFile = JMenu("File")

        var openFileItem = JMenuItem("Open...")
        menuFile.add(openFileItem)

        openFileItem.addActionListener {
            var fileChooser = JFileChooser()
            fileChooser.showDialog(this, "open")
            var file = fileChooser.selectedFile
            if (file != null)
                read(file.absolutePath)
        }

        menuBar.add(menuFile)
        jMenuBar = menuBar

        isVisible = true
        controller = AppController(this)
    }

    override fun repaint() {
        repaint()
    }

    fun read(file: String) {
        controller!!.setData(file)
    }

    override fun update(model: ModelInterface) {

        //если на фрейме уже есть художник, то увольняем его
        if (painter != null)
            remove(painter)

        when (model) {
            is Bmp8Model ->
                    painter = Bmp8Painter(model)

            is Bmp24Model ->
                    painter = Bmp24Painter(model)

            is Bmp32Model ->
                    painter = Bmp32Painter(model)

        }

        painter!!.setSize(model.width, model.height)

        val cns = getGridBagConstraints()
        contentPane.add(painter, cns)

        isVisible = true
    }

    fun getGridBagConstraints() : GridBagConstraints{
        val cns = GridBagConstraints()

        cns.weightx = 1.0
        cns.weighty = 1.0

        cns.ipadx = painter!!.width
        cns.ipady = painter!!.height

        cns.anchor = GridBagConstraints.CENTER
        return cns
    }
}