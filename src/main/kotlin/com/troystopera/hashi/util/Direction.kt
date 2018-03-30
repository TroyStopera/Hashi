package com.troystopera.hashi.util

import com.troystopera.hashi.gen.Random

enum class Direction {

    UP, DOWN, LEFT, RIGHT;

    fun translate(coordinate: Coordinate, dist: Int) = if (dist >= 0) when (this) {
        UP -> Coordinate(coordinate.row - dist, coordinate.col)
        DOWN -> Coordinate(coordinate.row + dist, coordinate.col)
        LEFT -> Coordinate(coordinate.row, coordinate.col - dist)
        RIGHT -> Coordinate(coordinate.row, coordinate.col + dist)
    } else throw IllegalArgumentException("Distance must be non-negative")

    fun opposite() = when (this) {
        UP -> DOWN
        DOWN -> UP
        LEFT -> RIGHT
        RIGHT -> LEFT
    }

    companion object {

        private val rOrder = arrayOf(UP, DOWN, LEFT, RIGHT)

        fun randomOrder(random: Random): MutableList<Direction> {
            //shuffle current oder
            for (i in 0..rOrder.size) {
                val swapIndex = random.nextInt(rOrder.size)
                val temp = rOrder[swapIndex]
                rOrder[swapIndex] = rOrder[i]
                rOrder[i] = temp
            }

            return rOrder.toMutableList()
        }

    }

}