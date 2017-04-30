package Model

import View.Observer

interface Observable {
    var observers: ArrayList<Observer>

    fun registerObserver (ob: Observer) {
        if (!observers.contains(ob))
            observers.add(ob)
    }

    fun removeObserver (ob: Observer) {
        observers.remove(ob)
    }

    fun notifyObservers()
}