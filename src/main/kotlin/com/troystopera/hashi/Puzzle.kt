package com.troystopera.hashi

import com.troystopera.hashi.util.*

class HashiPuzzle(val grid: Grid)

class Node(value: Int, coordinate: Coordinate) : NodeCell(value, coordinate) {

    private val bridges = arrayOfNulls<Bridge>(4)

    var currentBridgeValue = 0
        private set

    fun setBridge(direction: Direction, bridge: Bridge?) {
        currentBridgeValue -= bridges[direction.ordinal]?.cell?.value ?: 0
        currentBridgeValue += bridge?.cell?.value ?: 0
        bridges[direction.ordinal] = bridge
    }

    fun removeBridge(direction: Direction) = setBridge(direction, null)

    fun getBridge(direction: Direction) = bridges[direction.ordinal]

    fun getBridges(orientation: Orientation): List<Bridge> = when (orientation) {
        Orientation.VERTICAL -> listOfNotNull(getBridge(Direction.UP), getBridge(Direction.DOWN))
        Orientation.HORIZONTAL -> listOfNotNull(getBridge(Direction.LEFT), getBridge(Direction.RIGHT))
    }

}

class Bridge(val startNode: NodeCell, val endNode: NodeCell, width: Int) : Line(
        Direction.fromOrThrow(startNode.coordinate, endNode.coordinate).translate(startNode.coordinate, 1),
        Direction.fromOrThrow(endNode.coordinate, startNode.coordinate).translate(endNode.coordinate, 1)
) {

    var cell: BridgeCell = when (orientation) {
        Orientation.VERTICAL -> BridgeCell.get(width, Orientation.VERTICAL)
        Orientation.HORIZONTAL -> BridgeCell.get(width, Orientation.HORIZONTAL)
    }

    override fun equals(other: Any?): Boolean =
            (other is Bridge) && (startNode == other.startNode) && (endNode == other.endNode) && (cell == other.cell)

    override fun hashCode(): Int = 31 * super.hashCode() + cell.hashCode()

}