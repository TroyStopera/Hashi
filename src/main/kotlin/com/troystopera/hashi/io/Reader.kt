package com.troystopera.hashi.io

import com.troystopera.hashi.Bridge
import com.troystopera.hashi.HashiPuzzle
import com.troystopera.hashi.HashiPuzzleMetadata
import com.troystopera.hashi.Node
import com.troystopera.hashi.util.Coordinate

internal object Reader {

    fun read(bytes: ByteArray): HashiPuzzle {
        val reader = ByteArrayReader(bytes)

        //read the SERIALIZATION_VERSION
        reader.readByte()

        val metadata = readMetadata(reader)
        val nodes = readNodes(reader)
        val coordinateToNode = nodes.associateBy { it.coordinate }
        val solutionBridges = readBridges(reader, coordinateToNode)
        val currentBridges = readBridges(reader, coordinateToNode)

        return HashiPuzzle(metadata, nodes, solutionBridges, currentBridges)
    }

    private fun readMetadata(reader: ByteArrayReader): HashiPuzzleMetadata {
        val height = reader.readByte().toInt() + 1
        val width = reader.readByte().toInt() + 1
        val maxBridgeWidth = reader.readByte().toPositiveInt()
        val seed = reader.readLong()
        return HashiPuzzleMetadata(height, width, maxBridgeWidth, seed)
    }

    private fun readNodes(reader: ByteArrayReader): List<Node> {
        return (1..reader.readInt()).map {
            val value = reader.readByte().toPositiveInt()
            val coordinate = readCoordinate(reader)
            Node(value, coordinate)
        }
    }

    private fun readBridges(reader: ByteArrayReader, nodeMap: Map<Coordinate, Node>): List<Bridge> {
        return (1..reader.readInt()).map {
            val width = reader.readByte().toPositiveInt()
            val nodeOne = nodeMap.getValue(readCoordinate(reader))
            val nodeTwo = nodeMap.getValue(readCoordinate(reader))
            Bridge(nodeOne, nodeTwo, width)
        }
    }

    private fun readCoordinate(reader: ByteArrayReader): Coordinate {
        val row = reader.readByte().toPositiveInt()
        val col = reader.readByte().toPositiveInt()
        return Coordinate(row, col)
    }

}