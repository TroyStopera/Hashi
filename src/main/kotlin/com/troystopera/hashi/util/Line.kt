package com.troystopera.hashi.util

open class Line(val start: Coordinate, val end: Coordinate) : Iterable<Coordinate> {

    val orientation: Orientation = Orientation.fromOrThrow(start, end)

    val direction: Direction = Direction.fromOrThrow(start, end)

    val length = when (direction) {
        Direction.UP -> start.row - end.row
        Direction.DOWN -> end.row - start.row
        Direction.LEFT -> start.col - end.col
        Direction.RIGHT -> end.col - start.col
    }

    override fun iterator() = CoordinateIterator()

    companion object {

        fun isValid(start: Coordinate, end: Coordinate): Boolean = (start.row == end.row) || (start.col == end.col)

    }

    inner class CoordinateIterator : Iterator<Coordinate> {

        private val stop = direction.translate(end, 1)
        private var next = start

        override fun hasNext() = next != stop

        override fun next() = next.also { next = direction.translate(next, 1) }

    }

}