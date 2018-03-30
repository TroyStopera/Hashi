package com.troystopera.hashi.gen

import java.util.*

internal actual object Seed {

    private val random = Random()

    actual fun randomSeed() = random.nextLong()

}