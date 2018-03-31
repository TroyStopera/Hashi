package com.troystopera.hashi.gen

import com.troystopera.hashi.util.Grid

internal abstract class SpreadAlgorithm(
        protected val grid: Grid,
        protected val options: GenerationOptions,
        protected val random: Random,
        initialNode: GenNode
) {

    abstract fun nodeCount(): Int

    abstract fun canSpread(): Boolean

    abstract fun spread()

}