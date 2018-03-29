package com.troystopera.hashi.gen

enum class Direction {

    UP, DOWN, LEFT, RIGHT;

    fun translate(coordinate: Coordinate, dist: Int) = when (this) {
        Direction.UP -> Coordinate(coordinate.row - dist, coordinate.col)
        Direction.DOWN -> Coordinate(coordinate.row + dist, coordinate.col)
        Direction.LEFT -> Coordinate(coordinate.row, coordinate.col - dist)
        Direction.RIGHT -> Coordinate(coordinate.row, coordinate.col + dist)
    }

    fun opposite() = when (this) {
        Direction.UP -> DOWN
        Direction.DOWN -> UP
        Direction.LEFT -> RIGHT
        Direction.RIGHT -> LEFT
    }

}