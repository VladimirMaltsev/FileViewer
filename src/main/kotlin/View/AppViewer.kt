package View

import Controller.AppController
import Controller.ControllerInterface
import Model.Bmp24Model
import Model.Bmp32Model
import Model.Bmp8Model
import Model.ModelInterface
import View.Painters.*
import View.Painters.Painter
import java.awt.*
import javax.swing.*
import java.awt.GridBagConstraints




class AppViewer(title: String?) : ViewInterface(title){

    override var model: ModelInterface? = null
    override var controller: ControllerInterface? = null
    override var painter: Painter? = null

    init {
        setBounds(50, 50, 1000, 700)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        contentPane.layout = GridBagLayout()

        val menuBar = JMenuBar()
        val menuFile = JMenu("_File_")

        val openFileItem = JMenuItem("[+] Open...")
        menuFile.add(openFileItem)
        val closeFileItem = JMenuItem("[x] Close")
        menuFile.add(closeFileItem)

        //вешаем слушателся на пункт меню "open"
        openFileItem.addActionListener{

            val fileChooser = JFileChooser()
            fileChooser.showDialog(this, "open")
            val file = fileChooser.selectedFile

            if (file != null) {
                controller!!.readFile(file.absolutePath)
            }
        }

        //вешаем слушателя на кнопку "close"
        closeFileItem.addActionListener {
            if (painter != null)
                this.remove(painter)
            repaint()
        }

        menuBar.add(menuFile)
        jMenuBar = menuBar

        isVisible = true
        controller = AppController(this)
    }


    override fun update(model: ModelInterface) {

        //если у фрейме уже есть художник, то увольняем его
        if (painter != null )
            remove(painter)
        repaint()

        when (model) {
            is Bmp8Model ->
                    painter = Bmp8Painter(model)

            is Bmp24Model ->
                    painter = Bmp24Painter(model)

            is Bmp32Model ->
                    painter = Bmp32Painter(model)

            else ->
                    println("unsupported format")
        }

        painter!!.setSize(model.width, model.height)

        val cns = getGridBagConstraints()
        contentPane.add(painter, cns)

        repaint()
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