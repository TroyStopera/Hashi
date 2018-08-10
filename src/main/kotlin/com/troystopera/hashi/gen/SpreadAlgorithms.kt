package com.troystopera.hashi.gen

import com.troystopera.hashi.util.Direction

internal class SpreadBreadthFirst(
        grid: GenGrid, options: GenerationOptions, random: Random
) : BaseSpreadAlgorithm(grid, options, random) {

    private val queue = mutableListOf<GenNode>()
    private val secondary = mutableListOf<GenNode>()

    override fun nextSpread(): Pair<GenNode, Direction>? {
        val node = when {
            queue.isNotEmpty() -> queue.removeAt(0)
            secondary.isNotEmpty() -> secondary.removeAt(0)
            else -> return null
        }
        return when {
            node.hasUnchecked() -> Pair(node, node.getNextUnchecked())
            node.isSpreadable() -> Pair(node, node.getNextChecked())
            else -> null
        }
    }

    override fun addNode(node: GenNode) {
        super.addNode(node)
        queue.add(node)
    }

    override fun onNodePostSpread(node: GenNode) {
        if (node.hasUnchecked()) queue.add(0, node)
        else if (node.isSpreadable()) secondary.add(0, node)
    }

}

internal class SpreadDepthFirst(
        grid: GenGrid, options: GenerationOptions, random: Random
) : BaseSpreadAlgorithm(grid, options, random) {

    private val stack = mutableListOf<GenNode>()
    private val secondary = mutableListOf<GenNode>()

    override fun nextSpread(): Pair<GenNode, Direction>? {
        val node = stack.firstOrNull() ?: secondary.firstOrNull() ?: return null
        return when {
            node.hasUnchecked() -> Pair(node, node.getNextUnchecked())
            node.isSpreadable() -> Pair(node, node.getNextChecked())
            else -> null
        }
    }

    override fun addNode(node: GenNode) {
        super.addNode(node)
        stack.add(0, node)
    }

    //NOTE: remove() on a linked list is usually bad time complexity,
    // but at worst the element being removed here is at index 2, so it really is O(1)
    override fun onNodePostSpread(node: GenNode) {
        if (!node.hasUnchecked()) stack.remove(node)
        if (!node.isSpreadable()) secondary.remove(node)
    }

}