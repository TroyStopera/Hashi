package com.troystopera.hashi.gen

import com.troystopera.hashi.Bridge
import com.troystopera.hashi.BridgeCell
import com.troystopera.hashi.EmptyCell
import com.troystopera.hashi.PuzzleCell
import com.troystopera.hashi.util.*

internal class GenGrid(height: Int, width: Int) : Grid(height, width) {

    private val bridges = mutableMapOf<Coordinate, Bridge>()

    private var leftmostNode: GenNode? = null
    private var rightmostNode: GenNode? = null
    private var topmostNode: GenNode? = null
    private var bottommostNode: GenNode? = null

    fun getBridge(coordinate: Coordinate) = bridges[coordinate]

    fun getBridges() = bridges.values.toHashSet()

    fun getFarthestNodeLine(direction: Direction) = when (direction) {
        Direction.UP -> lineThrough(topmostNode!!.coordinate, Orientation.VERTICAL)
        Direction.DOWN -> lineThrough(bottommostNode!!.coordinate, Orientation.VERTICAL)
        Direction.LEFT -> lineThrough(leftmostNode!!.coordinate, Orientation.HORIZONTAL)
        Direction.RIGHT -> lineThrough(rightmostNode!!.coordinate, Orientation.HORIZONTAL)
    }

    fun moveLine(line: Line, direction: Direction, dist: Int) {
        line.mapNotNull { get(it) as? GenNode }.forEach {
            set(it.coordinate, EmptyCell)
            it.coordinate = direction.translate(it.coordinate, dist)
            set(it.coordinate, it)
        }

        line.mapNotNull { getBridge(it) }.toSet().filter { it.orientation != direction.orientation }.forEach {
            val newBridge = Bridge(get(direction.translate(it.start, dist)) as GenNode, get(direction.translate(it.end, dist)) as GenNode, it.cell.value)
            set(it, EmptyCell)
            set(newBridge, newBridge.cell)
        }

    }

    override fun set(coordinate: Coordinate, cell: PuzzleCell) {
        super.set(coordinate, cell)
        bridges.remove(coordinate)

        if (cell is GenNode) {
            leftmostNode = getFarthest(leftmostNode, cell, Direction.LEFT)
            rightmostNode = getFarthest(rightmostNode, cell, Direction.RIGHT)
            topmostNode = getFarthest(topmostNode, cell, Direction.UP)
            bottommostNode = getFarthest(bottommostNode, cell, Direction.DOWN)
        }
    }

    override fun set(line: Line, cell: PuzzleCell) {
        super.set(line, cell)
        if (cell is BridgeCell)
            (line as? Bridge)?.forEach { bridges[it] = line }
        else line.forEach { bridges.remove(it) }
    }

    private fun getFarthest(current: GenNode?, new: GenNode, direction: Direction) = when {
        current == null -> new
        new.coordinate.isFartherThan(current.coordinate, direction) -> new
        else -> current
    }

}