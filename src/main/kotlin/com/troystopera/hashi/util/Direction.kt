package com.troystopera.hashi.util

import com.troystopera.hashi.gen.Random

enum class Direction(val orientation: Orientation, private val transFun: (Coordinate, Int) -> Coordinate) {

    UP(Orientation.VERTICAL, { c, d -> Coordinate(c.row - d, c.col) }),
    DOWN(Orientation.VERTICAL, { c, d -> Coordinate(c.row + d, c.col) }),
    LEFT(Orientation.HORIZONTAL, { c, d -> Coordinate(c.row, c.col - d) }),
    RIGHT(Orientation.HORIZONTAL, { c, d -> Coordinate(c.row, c.col + d) });

    fun translate(coordinate: Coordinate, dist: Int): Coordinate = transFun(coordinate, dist)

    fun translate(line: Line, dist: Int): Line = Line(translate(line.start, dist), translate(line.end, dist))

    fun opposite() = when (this) {
        UP -> DOWN
        DOWN -> UP
        LEFT -> RIGHT
        RIGHT -> LEFT
    }

    companion object {

        private val rOrder = arrayOf(UP, DOWN, LEFT, RIGHT)

        internal fun randomOrder(random: Random): MutableList<Direction> {
            //shuffle current order
            for (i in 0 until rOrder.size) {
                val swapIndex = random.nextInt(rOrder.size)
                val temp = rOrder[swapIndex]
                rOrder[swapIndex] = rOrder[i]
                rOrder[i] = temp
            }
            return rOrder.toMutableList()
        }

        internal fun fromOrThrow(start: Coordinate, end: Coordinate): Direction = from(start, end)
                ?: throw IllegalArgumentException("$start and $end cannot form a line")

        fun from(start: Coordinate, end: Coordinate): Direction? = when (Orientation.from(start, end)) {
            Orientation.VERTICAL -> {
                if (start.row < end.row) Direction.DOWN
                else Direction.UP
            }
            Orientation.HORIZONTAL -> {
                if (start.col < end.col) Direction.RIGHT
                else Direction.LEFT
            }
            null -> null
        }

    }

}