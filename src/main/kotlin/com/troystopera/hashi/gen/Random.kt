package com.troystopera.hashi.gen

private const val MULTIPLIER: Long = 0x5DEECE66DL
private const val ADDEND: Long = 0xBL
private const val MASK: Long = (1L shl 48) - 1
private const val NORMAL_SAMPLES = 3

/*
    Simple adaptation of Java's java.util.Random
 */
class Random(seed: Long) {

    var seed = (seed xor MULTIPLIER) and MASK
        private set

    fun nextNormalInt(n: Int): Int {
        var value = 0
        for (i in 1..NORMAL_SAMPLES) value += nextInt(n)
        return value / NORMAL_SAMPLES
    }

    fun nextInt(n: Int): Int {
        var bits = next(31)
        var value = bits % n
        while (bits - value + (n - 1) < 0) {
            bits = next(31)
            value = bits % n
        }
        return value
    }

    fun nextDouble(): Double = ((next(26).toLong() shl 27) + next(27)) / (1L shl 53).toDouble()

    fun nextBoolean() = next(1) != 0

    private fun next(bits: Int): Int {
        seed = (seed * MULTIPLIER + ADDEND) and MASK
        return (seed ushr (48 - bits)).toInt()
    }

}

internal expect object Seed {
    fun randomSeed(): Long
}