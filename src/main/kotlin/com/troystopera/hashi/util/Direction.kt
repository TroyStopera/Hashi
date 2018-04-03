package com.troystopera.hashi.util

import com.troystopera.hashi.gen.Random

enum class Direction(private val transFun: (Coordinate, Int) -> Coordinate) {

    UP({ c, d -> Coordinate(c.row - d, c.col) }),
    DOWN({ c, d -> Coordinate(c.row + d, c.col) }),
    LEFT({ c, d -> Coordinate(c.row, c.col - d) }),
    RIGHT({ c, d -> Coordinate(c.row, c.col + d) });

    fun translate(coordinate: Coordinate, dist: Int): Coordinate = transFun(coordinate, dist)

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