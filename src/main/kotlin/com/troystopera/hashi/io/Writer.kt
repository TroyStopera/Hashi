package com.troystopera.hashi.io

import com.troystopera.hashi.Bridge
import com.troystopera.hashi.HashiPuzzle
import com.troystopera.hashi.HashiPuzzleMetadata
import com.troystopera.hashi.NodeCell
import com.troystopera.hashi.util.Coordinate

internal object Writer {

    const val SERIALIZATION_VERSION: Byte = 0

    fun write(puzzle: HashiPuzzle): ByteArray {
        val bytes = ByteArray(calculateBytes(puzzle))
        val writer = ByteArrayWriter(bytes)

        writer.writeByte(SERIALIZATION_VERSION)

        writeMetadata(puzzle.metadata, writer)
        writeNodes(puzzle.nodes, writer)
        writeBridges(puzzle.solutionBridges, writer)
        writeBridges(puzzle.getBridges(), writer)

        return bytes
    }

    private fun writeMetadata(metadata: HashiPuzzleMetadata, writer: ByteArrayWriter) {
        writer.writeByte((metadata.height - 1).toByte())
        writer.writeByte((metadata.width - 1).toByte())
        writer.writeByte(metadata.maxBridgeWidth.toByte())
        writer.writeLong(metadata.seed)
    }

    private fun writeNodes(nodes: Collection<NodeCell>, writer: ByteArrayWriter) {
        writer.writeInt(nodes.size)
        nodes.forEach {
            writer.writeByte(it.value.toByte())
            writeCoordinate(it.coordinate, writer)
        }
    }

    private fun writeBridges(bridges: Collection<Bridge>, writer: ByteArrayWriter) {
        writer.writeInt(bridges.size)
        bridges.forEach {
            writer.writeByte(it.cell.value.toByte())
            writeCoordinate(it.nodeOne.coordinate, writer)
            writeCoordinate(it.nodeTwo.coordinate, writer)
        }
    }

    private fun writeCoordinate(coordinate: Coordinate, writer: ByteArrayWriter) {
        writer.writeByte(coordinate.row.toByte())
        writer.writeByte(coordinate.col.toByte())
    }

    private fun calculateBytes(puzzle: HashiPuzzle): Int =
            1 + 11 + (4 + (3 * puzzle.nodes.size)) + (4 + (5 * puzzle.solutionBridges.size)) + (4 + (5 * puzzle.getBridges().size))

}