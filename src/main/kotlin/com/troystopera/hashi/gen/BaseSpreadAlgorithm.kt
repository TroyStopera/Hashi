package com.troystopera.hashi.gen

import com.troystopera.hashi.Bridge
import com.troystopera.hashi.BridgeCell
import com.troystopera.hashi.EmptyCell
import com.troystopera.hashi.NodeCell
import com.troystopera.hashi.util.*

internal abstract class BaseSpreadAlgorithm(
        private val grid: GenGrid,
        private val options: GenerationOptions,
        private val random: Random
) {

    private val nodes = mutableSetOf<GenNode>()
    private val bridges = mutableSetOf<Bridge>()

    fun nodeCount() = nodes.size

    protected abstract fun nextSpread(): Pair<GenNode, Direction>?

    protected abstract fun onNodePostSpread(node: GenNode)

    protected open fun addNode(node: GenNode) {
        nodes.add(node)
    }

    fun spread(): Boolean {
        //when first starting, add an initial node
        if (nodes.isEmpty())
            addNode(GenNode(0, random.nextNormalInt(options.height), random.nextNormalInt(options.width), random))

        // try to get a spreadable node and set it checked
        val (node, direction) = nextSpread() ?: return false
        node.setChecked(direction)

        // randomness for spreading a node
        // only spread if a random double (between 0 and 1) is less than the spread probability
        if (random.nextDouble() >= options.bridgeSpreadProb) {
            onNodePostSpread(node)
            return true
        }

        // generate a line te be used as the bridge to build
        val targetLine = Line(
                direction.translate(node.coordinate, 1),
                direction.translate(node.coordinate, determineRandomDistance(node, direction)))
        // determine the longest spread distance available given the targetLine
        val spreadableDistance = determineSpreadableDistance(targetLine)

        // if it can't spread more than two, remove the direction, and return
        if (spreadableDistance < 2) {
            node.remove(direction)
            onNodePostSpread(node)
            return true
        }

        // determine the new coordinate that can be spread to, as well as the cell currently there
        val newNodeCoordinate = direction.translate(node.coordinate, spreadableDistance)
        val cell = grid[newNodeCoordinate]

        // variables for the new bridge and node
        val bridgeWidth = 1 + random.nextInt(options.maxBridgeWidth)
        val newBridge: Bridge
        val newNode: GenNode?

        // if the current cell is a node, connect to it
        if (cell is GenNode) {
            cell += bridgeWidth
            cell.remove(direction.opposite())
            newNode = null
            newBridge = Bridge(node, cell, bridgeWidth)
        }
        // otherwise create a new node and bridge
        else {
            newNode = GenNode(0, newNodeCoordinate, random)
            newBridge = Bridge(node, newNode, bridgeWidth)

            //if the cell was a bridge that is being split
            if (cell is BridgeCell) {
                // account for the two bridges in the new node
                newNode += cell.value * 2
                when (cell.orientation) {
                    Orientation.VERTICAL -> {
                        newNode.remove(Direction.UP)
                        newNode.remove(Direction.DOWN)
                    }
                    Orientation.HORIZONTAL -> {
                        newNode.remove(Direction.LEFT)
                        newNode.remove(Direction.RIGHT)
                    }
                }

                // split the bridge and add the new parts
                val oldBridge = grid.getBridge(newNodeCoordinate)
                if (oldBridge != null) {
                    bridges.remove(oldBridge)
                    splitBridge(oldBridge, newNode).forEach {
                        grid[it] = it.cell
                    }
                }
            }
        }

        // update the grid with new cells
        grid[newBridge] = newBridge.cell
        if (newNode != null) {
            grid[newNodeCoordinate] = newNode
            addNode(newNode)
        }

        // update bridges and the node direction/value
        bridges.add(newBridge)
        node += bridgeWidth
        node.remove(direction)

        onNodePostSpread(node)
        return true
    }

    private fun determineSpreadableDistance(line: Line): Int {
        var dist = 0
        // iterate over line in order
        line.forEach {
            dist++
            val cell = grid[it]
            // if the cell at 'it' is not empty stop moving forward
            if (cell !== EmptyCell) {
                // if it is a non-splittable bridge move dist back one
                if (cell is BridgeCell && !isSplittable(it))
                    dist--
                return@forEach
            }
        }
        return dist
    }

    // TODO: reconsider algorithm for spread distance
    private fun determineRandomDistance(node: GenNode, direction: Direction): Int =
            2 + (random.nextInt(grid.distanceToEdge(node.coordinate, direction) - 1))

    private fun isSplittable(coordinate: Coordinate): Boolean {
        val cell = grid[coordinate]
        return if (cell is BridgeCell) {
            // check the bridge one cell in each direction... if it is still bridge it can be split
            when (cell.orientation) {
                Orientation.VERTICAL -> {
                    grid[Direction.UP.translate(coordinate, 1)] == cell &&
                            grid[Direction.DOWN.translate(coordinate, 1)] == cell
                }
                Orientation.HORIZONTAL -> {
                    grid[Direction.LEFT.translate(coordinate, 1)] == cell &&
                            grid[Direction.RIGHT.translate(coordinate, 1)] == cell
                }
            }
        } else false
    }

    private fun splitBridge(bridge: Bridge, middle: NodeCell) = listOf(
            Bridge(bridge.startNode, middle, bridge.cell.value),
            Bridge(middle, bridge.endNode, bridge.cell.value)
    )

}