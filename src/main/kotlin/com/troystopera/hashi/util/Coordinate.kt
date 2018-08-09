package com.troystopera.hashi.util

data class Coordinate(val row: Int, val col: Int) {

    fun isFartherThan(coordinate: Coordinate, direction: Direction): Boolean = when (direction) {
        Direction.UP -> row < coordinate.row
        Direction.DOWN -> row > coordinate.row
        Direction.LEFT -> col < coordinate.col
        Direction.RIGHT -> col > coordinate.col
    }

}