package View

import Model.ModelInterface

interface Observer{

    fun update(model : ModelInterface)
}