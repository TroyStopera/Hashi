package com.troystopera.hashi

import com.troystopera.hashi.util.*

class HashiPuzzle internal constructor(
        val metadata: HashiPuzzleMetadata,
        internal val nodes: List<Node>,
        internal val solutionBridges: List<Bridge>,
        currentBridges: List<Bridge> = listOf()
) {

    private val grid = Grid(metadata.height, metadata.width)
    private val bridges = mutableSetOf<Bridge>()

    private var incompleteNodes = nodes.size
    private var onCellUpdate: ((coordinate: Coordinate, cell: PuzzleCell) -> Unit)? = null
    private var onNodeStateChange: ((node: NodeCell, complete: Boolean) -> Unit)? = null
    private var onPuzzleSolved: (() -> Unit)? = null

    init {
        nodes.forEach { node ->
            grid[node.coordinate] = node
            node.setStateChangeListener { nodeChanged(node, it) }
        }
        currentBridges.forEach { addBridge(it) }
    }

    fun setCellUpdateListener(listener: ((coordinate: Coordinate, cell: PuzzleCell) -> Unit)?) {
        onCellUpdate = listener
        grid.all().forEach { onCellUpdate?.invoke(it.first, it.second) }
    }

    fun setNodeStateChangeListener(listener: ((node: NodeCell, complete: Boolean) -> Unit)?) {
        onNodeStateChange = listener
        nodes.forEach { if (it.isComplete) onNodeStateChange?.invoke(it, true) }
    }

    fun setPuzzleSolvedListener(listener: (() -> Unit)?) {
        onPuzzleSolved = listener
        if (isSolved()) onPuzzleSolved?.invoke()
    }

    internal fun getBridges() = bridges.toList()

    fun addBridge(bridge: Bridge) {
        grid[bridge] = bridge.cell

        val start = grid[bridge.nodeOne.coordinate] as Node
        val end = grid[bridge.nodeTwo.coordinate] as Node

        val startDir = Direction.fromOrThrow(start.coordinate, end.coordinate)
        val endDir = Direction.fromOrThrow(end.coordinate, start.coordinate)

        //remove old bridge if it exists
        start.getBridge(startDir)?.let { bridges.remove(it) }

        start.setBridge(startDir, bridge)
        end.setBridge(endDir, bridge)

        bridges.add(bridge)
        bridge.forEach { onCellUpdate?.invoke(it, bridge.cell) }
    }

    fun removeBridge(bridge: Bridge) {
        grid[bridge] = EmptyCell
        val start = grid[bridge.nodeOne.coordinate] as Node
        val end = grid[bridge.nodeTwo.coordinate] as Node
        start.removeBridge(Direction.fromOrThrow(start.coordinate, end.coordinate))
        end.removeBridge(Direction.fromOrThrow(end.coordinate, start.coordinate))

        bridges.remove(bridge)
        bridge.forEach { onCellUpdate?.invoke(it, EmptyCell) }
    }

    fun solve() {
        val bridges = bridges.minus(solutionBridges)
        bridges.forEach { removeBridge(it) }
        solutionBridges.minus(bridges).forEach { addBridge(it) }
    }

    fun isSolved() = incompleteNodes == 0 && isConnected()

    private fun nodeChanged(node: Node, complete: Boolean) {
        if (complete) incompleteNodes--
        else incompleteNodes++

        onNodeStateChange?.invoke(node, complete)
        if (isSolved()) onPuzzleSolved?.invoke()
    }

    private fun isConnected(): Boolean {
        val checked = hashSetOf<Coordinate>()
        nodes.firstOrNull()?.let { dfs(it, checked) }
        return checked.size == nodes.size
    }

    private fun dfs(node: Node, checked: HashSet<Coordinate>) {
        if (checked.contains(node.coordinate)) return

        checked.add(node.coordinate)
        node.getBridges().forEach {
            val next = if (it.nodeOne == node) it.nodeTwo else it.nodeOne
            dfs(grid[next.coordinate] as Node, checked)
        }
    }

}

data class HashiPuzzleMetadata(val height: Int, val width: Int, val maxBridgeWidth: Int, val seed: Long)