package com.troystopera.hashi.gen

import com.troystopera.hashi.util.Direction

internal class SpreadBreadthFirst(
        grid: GenGrid,
        options: GenerationOptions,
        random: Random,
        initialNode: GenNode
) : SpreadAlgorithm(grid, options, random) {

    init {
        addNode(initialNode)
    }

    override fun nextSpread(): Pair<GenNode, Direction>? {
        TODO("not implemented")
    }

    override fun onNodeAdded(node: GenNode) {
        TODO("not implemented")
    }

    override fun onNodePostSpread(node: GenNode) {
        TODO("not implemented")
    }

}