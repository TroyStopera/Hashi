package com.troystopera.hashi.util

enum class Orientation {

    VERTICAL,
    HORIZONTAL;

    companion object {

        internal fun fromOrThrow(start: Coordinate, end: Coordinate): Orientation = from(start, end)
                ?: throw IllegalArgumentException("$start and $end cannot form a line")

        fun from(start: Coordinate, end: Coordinate): Orientation? {
            if (Line.isValid(start, end)) {
                if (start.row == end.row) return Orientation.HORIZONTAL
                if (start.col == end.col) return Orientation.VERTICAL
            }
            return null
        }

    }
}