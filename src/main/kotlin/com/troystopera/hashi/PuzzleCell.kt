package com.troystopera.hashi

sealed class PuzzleCell {

    fun isEmpty() = this == EmptyCell

    fun isNotEmpty() = this != EmptyCell

}

object EmptyCell : PuzzleCell()

open class NodeCell protected constructor(val value: Int) : PuzzleCell() {

    companion object {

        private val cache = hashMapOf<Int, NodeCell>()

        operator fun get(value: Int) = cache.getOrPut(value, { NodeCell(value) })

    }

}

sealed class BridgeCell(val value: Int) : PuzzleCell() {

    fun isVertical() = this is VerticalBridge

    fun isHorizontal() = this is HorizontalBridge

}

class VerticalBridge private constructor(value: Int) : BridgeCell(value) {

    companion object {

        private val cache = hashMapOf<Int, VerticalBridge>()

        operator fun get(value: Int) = cache.getOrPut(value, { VerticalBridge(value) })

    }

}

class HorizontalBridge private constructor(value: Int) : BridgeCell(value) {

    companion object {

        private val cache = hashMapOf<Int, HorizontalBridge>()

        operator fun get(value: Int) = cache.getOrPut(value, { HorizontalBridge(value) })

    }

}