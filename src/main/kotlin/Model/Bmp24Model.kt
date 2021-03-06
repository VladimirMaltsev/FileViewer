package Model

import View.Observer

class Bmp24Model : ModelInterface{


    override var height: Int = 0
    override var width: Int = 0
    override var size: Int = 0
    override var bytesForPixel: Int = 0
    override var pixelStartIndex: Int = 0
    override var countColorsInTable: Int = 0
    override var pixelArray: ByteArray? = null
    override var colorTable: ByteArray?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override var observers: ArrayList<Observer> = ArrayList()

    override fun inflate(data: ByteArray) {
        size = getValue(data, 0x02, 4)
        width = getValue(data, 0x12, 4)
        height = Math.abs(getValue(data, 0x16, 4))
        bytesForPixel = getValue(data, 0x1C, 1)
        pixelStartIndex = getValue(data, 0x0A, 4)
        pixelArray = data.copyOfRange(pixelStartIndex, size)

        notifyObservers()
    }



}