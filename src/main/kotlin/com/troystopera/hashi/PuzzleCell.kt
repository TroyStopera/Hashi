package com.troystopera.hashi

import com.troystopera.hashi.util.Orientation

sealed class PuzzleCell {

    fun isEmpty() = this == EmptyCell

    fun isNotEmpty() = this != EmptyCell

}

object EmptyCell : PuzzleCell()

open class NodeCell protected constructor(open val value: Int) : PuzzleCell() {

    companion object {

        private val cache = hashMapOf<Int, NodeCell>()

        operator fun get(value: Int) = cache.getOrPut(value, { NodeCell(value) })

    }

}

class BridgeCell(val value: Int, val orientation: Orientation) : PuzzleCell() {

    companion object {

        private val vCache = hashMapOf<Int, BridgeCell>()
        private val hCache = hashMapOf<Int, BridgeCell>()

        fun get(value: Int, orientation: Orientation) = when (orientation) {
            Orientation.VERTICAL -> vCache.getOrPut(value, { BridgeCell(value, orientation) })
            Orientation.HORIZONTAL -> hCache.getOrPut(value, { BridgeCell(value, orientation) })
        }

    }

}