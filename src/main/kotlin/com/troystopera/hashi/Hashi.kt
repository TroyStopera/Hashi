package com.troystopera.hashi

import com.troystopera.hashi.io.Reader
import com.troystopera.hashi.io.Writer

object Hashi {

    fun toBytes(puzzle: HashiPuzzle) = Writer.write(puzzle)

    fun fromBytes(bytes: ByteArray) = Reader.read(bytes)

}