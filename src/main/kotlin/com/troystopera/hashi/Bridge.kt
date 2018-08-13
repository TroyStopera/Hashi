package com.troystopera.hashi

import com.troystopera.hashi.util.Coordinate
import com.troystopera.hashi.util.Direction
import com.troystopera.hashi.util.Line
import com.troystopera.hashi.util.Orientation

class Bridge(val nodeOne: NodeCell, val nodeTwo: NodeCell, width: Int) : Iterable<Coordinate> {

    private val line = Line(
            Direction.fromOrThrow(nodeOne.coordinate, nodeTwo.coordinate).translate(nodeOne.coordinate, 1),
            Direction.fromOrThrow(nodeTwo.coordinate, nodeOne.coordinate).translate(nodeTwo.coordinate, 1)
    )
    val orientation = line.orientation

    var cell: BridgeCell = when (orientation) {
        Orientation.VERTICAL -> BridgeCell.get(width, Orientation.VERTICAL)
        Orientation.HORIZONTAL -> BridgeCell.get(width, Orientation.HORIZONTAL)
    }

    override fun iterator(): Iterator<Coordinate> = line.iterator()

    override fun equals(other: Any?): Boolean = (other is Bridge) && (cell == other.cell) &&
            ((nodeOne == other.nodeOne && nodeTwo == other.nodeTwo) || (nodeOne == other.nodeTwo && nodeTwo == other.nodeOne))

    override fun hashCode(): Int = 31 * super.hashCode() + cell.hashCode()

}