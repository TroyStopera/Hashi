package com.troystopera.hashi.gen

import com.troystopera.hashi.Constraints
import com.troystopera.hashi.HashiPuzzle

class Generator(val options: GenerationOptions) {

    fun generate(seed: Long = Seed.randomSeed()): HashiPuzzle {
        val random = Random(seed)
        val grid = GenGrid(options.height, options.width)
        val initialNode = GenNode(0, random.nextNormalInt(options.height), random.nextNormalInt(options.width), random)
        val spreadAlg = options.algorithm.getInstance(grid, options, random, initialNode)

        while (spreadAlg.nodeCount() < options.nodeCount && spreadAlg.spread());

        return HashiPuzzle(grid)
    }

}

class GenerationOptions(
        height: Int = 7,
        width: Int = 7,
        nodeCount: Int = 12,
        bridgeDensity: Double = 0.5,
        maxBridgeWidth: Int = 2,
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

    internal val bridgeSpreadProb: Double = 0.5 + (0.5 * bridgeDensity)

}

enum class GenerationAlgorithm(private val factory: (GenGrid, GenerationOptions, Random, GenNode) -> SpreadAlgorithm) {

    BREADTH_FIRST_SPREAD({ grid, options, random, initial -> SpreadBreadthFirst(grid, options, random, initial) });

    internal fun getInstance(grid: GenGrid, options: GenerationOptions, random: Random, initialNode: GenNode) =
            factory(grid, options, random, initialNode)

}