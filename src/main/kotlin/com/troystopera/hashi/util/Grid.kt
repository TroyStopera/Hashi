package com.troystopera.hashi.util

import com.troystopera.hashi.EmptyCell
import com.troystopera.hashi.PuzzleCell

open class Grid(val height: Int, val width: Int) {

    private val cells = hashMapOf<Coordinate, PuzzleCell>()

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

    open operator fun set(line: Line, cell: PuzzleCell) {
        validateCoordinate(line.start)
        validateCoordinate(line.end)
        line.forEach {
            if (cell == EmptyCell) cells.remove(it)
            else cells[it] = cell
        }
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

    private fun validateCoordinate(coordinate: Coordinate) {
        if (coordinate.row < 0 || coordinate.row >= height)
            throw IndexOutOfBoundsException("Row ${coordinate.row} out of bounds")
        if (coordinate.col < 0 || coordinate.col >= width)
            throw IndexOutOfBoundsException("Column ${coordinate.col} out of bounds")
    }

}