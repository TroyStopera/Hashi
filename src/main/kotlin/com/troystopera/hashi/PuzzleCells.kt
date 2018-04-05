package com.troystopera.hashi

import com.troystopera.hashi.util.Coordinate
import com.troystopera.hashi.util.Orientation

sealed class PuzzleCell {

    fun isEmpty() = this == EmptyCell

    fun isNotEmpty() = this != EmptyCell

}

object EmptyCell : PuzzleCell()

abstract class NodeCell(open val value: Int, val coordinate: Coordinate) : PuzzleCell()

class BridgeCell private constructor(val value: Int, val orientation: Orientation) : PuzzleCell() {

    companion object {

        private val vCache = hashMapOf<Int, BridgeCell>()
        private val hCache = hashMapOf<Int, BridgeCell>()

        fun get(value: Int, orientation: Orientation) = when (orientation) {
            Orientation.VERTICAL -> vCache.getOrPut(value, { BridgeCell(value, orientation) })
            Orientation.HORIZONTAL -> hCache.getOrPut(value, { BridgeCell(value, orientation) })
        }

    }

}