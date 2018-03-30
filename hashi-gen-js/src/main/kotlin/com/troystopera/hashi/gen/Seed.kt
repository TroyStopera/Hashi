package com.troystopera.hashi.gen

import kotlin.js.Math

internal actual object Seed {

    actual fun randomSeed() = (Math.random() * Long.MAX_VALUE).toLong()

}