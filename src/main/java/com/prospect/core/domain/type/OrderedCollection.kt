package com.prospect.core.domain.type

class OrderedCollection<E>(val elements: List<E>) {

    companion object {
        fun <E> of(elements: List<E>) = OrderedCollection(elements)
    }

    fun divideByPriority(underFilter: (E) -> Boolean): Pair<OrderedCollection<E>, OrderedCollection<E>> {
        val over = elements.filterNot(underFilter)
        val under = elements.filter(underFilter)
        return Pair(of(over), of(under))
    }

    fun downOrder(downOperation: (E) -> E) = of(elements.map(downOperation))

    fun upOrder(upOperation: (E) -> E) = of(elements.map(upOperation))

    fun add(element: E): OrderedCollection<E> {
        val mutableList = mutableListOf<E>()
        mutableList.addAll(elements)
        mutableList.add(element)
        return of(mutableList)
    }

    fun remove(element: E): OrderedCollection<E> {
        val mutableList = mutableListOf<E>()
        mutableList.addAll(elements)
        mutableList.remove(element)
        return of(mutableList)
    }

    fun addAll(orderedCollection: OrderedCollection<E>) =
            orderedCollection.elements.foldRight(this) { element, acc -> acc.add(element) }

    fun removeAll(orderedCollection: OrderedCollection<E>) =
            orderedCollection.elements.foldRight(this) { element, acc -> acc.remove(element) }

}