package com.troystopera.hashi.gen

import com.troystopera.hashi.HashiPuzzle
import com.troystopera.hashi.util.Grid

class Generator(val options: GenerationOptions) {

    fun generate(seed: Long = Seed.randomSeed()): HashiPuzzle {
        val random = Random(seed)
        val grid = Grid(options.height, options.width)
        val initialNode = GenNode(0, random.nextNormalInt(options.height), random.nextNormalInt(options.width), random)
        val spreadAlg = options.algorithm.getInstance(grid, options, random, initialNode)

        while (spreadAlg.nodeCount() < options.targetNodeCount && spreadAlg.canSpread())
            spreadAlg.spread()

        return HashiPuzzle(grid)
    }

}

data class GenerationOptions(
        val height: Int = 7,
        val width: Int = 7,
        val targetNodeCount: Int = 12,
        val maxBridgeWidth: Int = 2,
        val algorithm: GenerationAlgorithm
)

enum class GenerationAlgorithm {

    ;

    internal abstract fun getInstance(grid: Grid, options: GenerationOptions, random: Random, initialNode: GenNode): SpreadAlgorithm

}