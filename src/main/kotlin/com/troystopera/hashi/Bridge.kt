package com.troystopera.hashi

import com.troystopera.hashi.util.Direction
import com.troystopera.hashi.util.Line
import com.troystopera.hashi.util.Orientation

class Bridge(val startNode: NodeCell, val endNode: NodeCell, width: Int) : Line(
        Direction.fromOrThrow(startNode.coordinate, endNode.coordinate).translate(startNode.coordinate, 1),
        Direction.fromOrThrow(endNode.coordinate, startNode.coordinate).translate(endNode.coordinate, 1)
) {

    var cell: BridgeCell = when (orientation) {
        Orientation.VERTICAL -> BridgeCell.get(width, Orientation.VERTICAL)
        Orientation.HORIZONTAL -> BridgeCell.get(width, Orientation.HORIZONTAL)
    }

    override fun hashCode(): Int = startNode.hashCode() xor endNode.hashCode()

    override fun equals(other: Any?): Boolean {
        if (other !is Bridge) return false
        return (startNode == other.startNode) && (endNode == other.endNode) && (cell == other.cell)
    }

}