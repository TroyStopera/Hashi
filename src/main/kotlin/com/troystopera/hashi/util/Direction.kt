package com.troystopera.hashi.util

import com.troystopera.hashi.gen.Random

enum class Direction(private val rowChange: Int, private val columnChange: Int) {

    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1);

    fun translate(coordinate: Coordinate, dist: Int): Coordinate {
        return Coordinate(coordinate.row + (rowChange * dist), coordinate.col + (columnChange * dist))
    }

    fun opposite() = when (this) {
        UP -> DOWN
        DOWN -> UP
        LEFT -> RIGHT
        RIGHT -> LEFT
    }

    companion object {

        private val rOrder = arrayOf(UP, DOWN, LEFT, RIGHT)

        fun randomOrder(random: Random): MutableList<Direction> {
            //shuffle current order
            for (i in 0 until rOrder.size) {
                val swapIndex = random.nextInt(rOrder.size)
                val temp = rOrder[swapIndex]
                rOrder[swapIndex] = rOrder[i]
                rOrder[i] = temp
            }
            return rOrder.toMutableList()
        }

    }

}