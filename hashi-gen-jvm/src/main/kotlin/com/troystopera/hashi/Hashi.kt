package com.troystopera.hashi

import java.io.File
import java.util.*

fun Hashi.toBase64(puzzle: HashiPuzzle) = Base64.getEncoder().encodeToString(toBytes(puzzle))

fun Hashi.fromBase64(base64: String) = fromBytes(Base64.getDecoder().decode(base64))

fun Hashi.toFile(puzzle: HashiPuzzle, path: String) = File(path).writeBytes(toBytes(puzzle))

fun Hashi.fromFile(path: String) = fromBytes(File(path).readBytes())

fun Hashi.toFile(puzzle: HashiPuzzle, file: File) = file.writeBytes(toBytes(puzzle))

fun Hashi.fromFile(file: File) = fromBytes(file.readBytes())