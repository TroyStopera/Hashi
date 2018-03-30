package com.troystopera.hashi.gen

/*
    Simple adaptation of Java's java.util.Random
 */
class Random(seed: Long) {

    var seed = (seed xor multiplier) and mask
        private set

    fun nextInt(n: Int): Int {
        var bits = next(31)
        var value = bits % n
        while (bits - value + (n - 1) < 0) {
            bits = next(31)
            value = bits % n
        }
        return value
    }

    fun nextBoolean() = next(1) != 0

    private fun next(bits: Int): Int {
        seed = (seed * multiplier + addend) and mask
        return (seed ushr (48 - bits)).toInt()
    }

    companion object {
        private const val multiplier: Long = 0x5DEECE66DL
        private const val addend: Long = 0xBL
        private const val mask: Long = (1L shl 48) - 1
    }

}

internal expect object Seed {
    fun randomSeed(): Long
}