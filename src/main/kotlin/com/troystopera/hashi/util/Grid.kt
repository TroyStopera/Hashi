package com.troystopera.hashi.util

import com.troystopera.hashi.EmptyCell
import com.troystopera.hashi.PuzzleCell

open class Grid(val height: Int, val width: Int) {

    private val cells = hashMapOf<Coordinate, PuzzleCell>()

    fun all() = cells.entries.map { Pair(it.key, it.value) }

    fun clear() = cells.clear()

    operator fun get(coordinate: Coordinate): PuzzleCell {
        validateCoordinate(coordinate)
        return cells.getOrElse(coordinate, { EmptyCell })
    }

    open operator fun set(coordinate: Coordinate, cell: PuzzleCell) {
        validateCoordinate(coordinate)
        if (cell == EmptyCell) cells.remove(coordinate)
        else cells[coordinate] = cell
    }

    open operator fun set(line: Iterable<Coordinate>, cell: PuzzleCell) = line.forEach { set(it, cell) }

    fun lineThrough(coordinate: Coordinate, orientation: Orientation): Line = when (orientation) {
        Orientation.VERTICAL -> Line(Coordinate(0, coordinate.col), Coordinate(height - 1, coordinate.col))
        Orientation.HORIZONTAL -> Line(Coordinate(coordinate.row, 0), Coordinate(coordinate.row, width - 1))
    }

    fun distanceToEdge(coordinate: Coordinate, direction: Direction): Int = try {
        validateCoordinate(coordinate)
        when (direction) {
            Direction.UP -> coordinate.row
            Direction.DOWN -> height - coordinate.row
            Direction.LEFT -> coordinate.col
            Direction.RIGHT -> width - coordinate.col
        }
    } catch (ex: IndexOutOfBoundsException) {
        -1
    }

    fun distanceToEdge(line: Line, direction: Direction) = when (line.orientation) {
        Orientation.VERTICAL -> when (direction) {
            Direction.UP,
            Direction.DOWN -> distanceToEdge(if (line.direction == direction) line.end else line.start, direction)
            Direction.LEFT,
            Direction.RIGHT -> distanceToEdge(line.start, direction)
        }
        Orientation.HORIZONTAL -> when (direction) {
            Direction.UP,
            Direction.DOWN -> distanceToEdge(line.start, direction)
            Direction.LEFT,
            Direction.RIGHT -> distanceToEdge(if (line.direction == direction) line.end else line.start, direction)
        }
    }

    private fun validateCoordinate(coordinate: Coordinate) {
        if (coordinate.row < 0 || coordinate.row >= height)
            throw IndexOutOfBoundsException("Row ${coordinate.row} out of bounds")
        if (coordinate.col < 0 || coordinate.col >= width)
            throw IndexOutOfBoundsException("Column ${coordinate.col} out of bounds")
    }

}