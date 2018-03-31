package com.troystopera.hashi.util

class Line(val start: Coordinate, val end: Coordinate) : Iterable<Coordinate> {

    val orientation: Orientation = when {
        start == end -> throw IllegalArgumentException("Start and End must be different coordinates")
        start.row == end.row -> Orientation.HORIZONTAL
        start.col == end.col -> Orientation.VERTICAL
        else -> throw IllegalArgumentException("$start and $end cannot form a line")
    }

    val direction: Direction = when (orientation) {
        Orientation.VERTICAL -> {
            if (start.row < end.row) Direction.DOWN
            else Direction.UP
        }
        Orientation.HORIZONTAL -> {
            if (start.col < end.col) Direction.RIGHT
            else Direction.LEFT
        }
    }

    override fun iterator() = CoordinateIterator()

    inner class CoordinateIterator : Iterator<Coordinate> {

        private val stop = direction.translate(end, 1)
        private var next = start

        override fun hasNext() = next != stop

        override fun next() = next.also { next = direction.translate(next, 1) }

    }

}