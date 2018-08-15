package com.troystopera.hashi

import com.troystopera.hashi.util.Coordinate
import com.troystopera.hashi.util.Direction
import com.troystopera.hashi.util.Orientation

class Node(value: Int, coordinate: Coordinate) : NodeCell(value, coordinate) {

    private var onStateChange: ((completed: Boolean) -> Unit)? = null

    private val bridges = arrayOfNulls<Bridge>(4)

    private var currentBridgeValue = 0

    val isComplete: Boolean
        get() = currentBridgeValue == value

    internal fun setStateChangeListener(listener: ((completed: Boolean) -> Unit)?) {
        onStateChange = listener
    }

    internal fun setBridge(direction: Direction, bridge: Bridge?) {
        val oldValue = currentBridgeValue
        currentBridgeValue -= bridges[direction.ordinal]?.cell?.value ?: 0
        currentBridgeValue += bridge?.cell?.value ?: 0
        bridges[direction.ordinal] = bridge

        if (oldValue != currentBridgeValue) {
            if (oldValue == value) onStateChange?.invoke(false)
            else if (currentBridgeValue == value) onStateChange?.invoke(true)
        }
    }

    internal fun removeBridge(direction: Direction) = setBridge(direction, null)

    fun getBridge(direction: Direction) = bridges[direction.ordinal]

    fun getBridges() = listOfNotNull(getBridge(Direction.UP), getBridge(Direction.DOWN), getBridge(Direction.LEFT), getBridge(Direction.RIGHT))

    fun getBridges(orientation: Orientation): List<Bridge> = when (orientation) {
        Orientation.VERTICAL -> listOfNotNull(getBridge(Direction.UP), getBridge(Direction.DOWN))
        Orientation.HORIZONTAL -> listOfNotNull(getBridge(Direction.LEFT), getBridge(Direction.RIGHT))
    }

}