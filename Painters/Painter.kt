package Painters

import Model.ModelInterface
import javax.swing.JPanel

open abstract class Painter(var model: ModelInterface) : JPanel()