package com.troystopera.hashi.gen

import com.troystopera.hashi.Constraints
import com.troystopera.hashi.HashiPuzzle
import com.troystopera.hashi.util.Direction

class Generator(val options: GenerationOptions) {

    fun generate(seed: Long = Seed.randomSeed()): HashiPuzzle {
        val grid = GenGrid(options.height, options.width)
        val spreadAlg = options.algorithm.getInstance(grid, options, Random(seed))

        //loop while more nodes are still needed AND the algorithm is successfully spreading
        while (spreadAlg.nodeCount() < options.nodeCount && spreadAlg.spread());

        //make the nodes and bridges fit to the edges of the grid
        stretchGrid(grid)

        return HashiPuzzle(grid)
    }

    private fun stretchGrid(genGrid: GenGrid) {
        for (dir in Direction.values()) {
            val farthestLine = genGrid.getFarthestNodeLine(dir)
            genGrid.moveLine(farthestLine, dir, genGrid.distanceToEdge(farthestLine, dir))
        }
    }

}

class GenerationOptions(
        height: Int,
        width: Int,
        nodeCount: Int,
        bridgeDensity: Double,
        maxBridgeWidth: Int,
        val algorithm: GenerationAlgorithm = GenerationAlgorithm.BREADTH_FIRST_SPREAD
) {

    val height: Int = when {
        height < Constraints.MIN_PUZZLE_HEIGHT -> Constraints.MIN_PUZZLE_HEIGHT
        height > Constraints.MAX_PUZZLE_HEIGHT -> Constraints.MAX_PUZZLE_HEIGHT
        else -> height
    }

    val width: Int = when {
        height < Constraints.MIN_PUZZLE_WIDTH -> Constraints.MIN_PUZZLE_WIDTH
        height > Constraints.MAX_PUZZLE_WIDTH -> Constraints.MAX_PUZZLE_WIDTH
        else -> height
    }

    val nodeCount: Int = when {
        nodeCount > ((height * width) * 0.5) -> ((height * width) * 0.5).toInt()
        else -> nodeCount
    }

    val bridgeDensity: Double = when {
        bridgeDensity < Constraints.MIN_BRIDGE_DENSITY -> Constraints.MIN_BRIDGE_DENSITY
        bridgeDensity > Constraints.MAX_BRIDGE_DENSITY -> Constraints.MAX_BRIDGE_DENSITY
        else -> bridgeDensity
    }

    val maxBridgeWidth: Int = when {
        maxBridgeWidth < Constraints.MIN_BRIDGE_WIDTH -> Constraints.MIN_BRIDGE_WIDTH
        maxBridgeWidth > Constraints.MAX_BRIDGE_WIDTH -> Constraints.MAX_BRIDGE_WIDTH
        else -> maxBridgeWidth
    }

    internal val bridgeSpreadProb: Double = 0.5 + (0.5 * this.bridgeDensity)

}

enum class GenerationAlgorithm {

    BREADTH_FIRST_SPREAD, DEPTH_FIRST_SPREAD;

    internal fun getInstance(grid: GenGrid, options: GenerationOptions, random: Random) = when (this) {
        BREADTH_FIRST_SPREAD -> SpreadBreadthFirst(grid, options, random)
        DEPTH_FIRST_SPREAD -> SpreadDepthFirst(grid, options, random)
    }

}