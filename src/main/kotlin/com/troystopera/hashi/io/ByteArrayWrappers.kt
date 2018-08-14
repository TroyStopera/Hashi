package com.troystopera.hashi.io

internal class ByteArrayWriter(private val bytes: ByteArray) {

    private var index = 0

    fun writeByte(byte: Byte) = bytes.set(index++, byte)

    fun writeInt(int: Int) {
        bytes[index++] = (int ushr 24).toByte()
        bytes[index++] = (int ushr 16).toByte()
        bytes[index++] = (int ushr 8).toByte()
        bytes[index++] = (int ushr 0).toByte()
    }

    fun writeLong(long: Long) {
        bytes[index++] = (long ushr 56).toByte()
        bytes[index++] = (long ushr 48).toByte()
        bytes[index++] = (long ushr 40).toByte()
        bytes[index++] = (long ushr 32).toByte()
        bytes[index++] = (long ushr 24).toByte()
        bytes[index++] = (long ushr 16).toByte()
        bytes[index++] = (long ushr 8).toByte()
        bytes[index++] = (long ushr 0).toByte()
    }

}

internal class ByteArrayReader(private val bytes: ByteArray) {

    private var index = 0

    fun readByte(): Byte = bytes[index++]

    fun readInt(): Int = (bytes[index++].toPositiveInt() shl 24) or
            (bytes[index++].toPositiveInt() shl 16) or
            (bytes[index++].toPositiveInt() shl 8) or
            (bytes[index++].toPositiveInt() shl 0)

    fun readLong(): Long = (bytes[index++].toPositiveLong() shl 56) or
            (bytes[index++].toPositiveLong() shl 48) or
            (bytes[index++].toPositiveLong() shl 40) or
            (bytes[index++].toPositiveLong() shl 32) or
            (bytes[index++].toPositiveLong() shl 24) or
            (bytes[index++].toPositiveLong() shl 16) or
            (bytes[index++].toPositiveLong() shl 8) or
            (bytes[index++].toPositiveLong() shl 0)

}

internal fun Byte.toPositiveInt() = toInt() and 0xFF

internal fun Byte.toPositiveLong() = toLong() and 0xFF