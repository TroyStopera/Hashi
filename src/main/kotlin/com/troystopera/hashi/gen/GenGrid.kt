package com.troystopera.hashi.gen

import com.troystopera.hashi.Bridge
import com.troystopera.hashi.PuzzleCell
import com.troystopera.hashi.util.Coordinate
import com.troystopera.hashi.util.Grid
import com.troystopera.hashi.util.Line

internal class GenGrid(height: Int, width: Int) : Grid(height, width) {

    private val bridges = mutableMapOf<Coordinate, Bridge>()

    fun getBridge(coordinate: Coordinate) = bridges[coordinate]

    fun getBridges() = bridges.values.toHashSet()

    override fun set(coordinate: Coordinate, cell: PuzzleCell) {
        super.set(coordinate, cell)
        bridges.remove(coordinate)
    }

    override fun set(line: Line, cell: PuzzleCell) {
        super.set(line, cell)
        (line as? Bridge)?.forEach { bridges[it] = line }
    }

}