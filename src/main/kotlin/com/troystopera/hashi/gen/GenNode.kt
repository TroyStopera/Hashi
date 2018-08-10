package com.troystopera.hashi.gen

import com.troystopera.hashi.NodeCell
import com.troystopera.hashi.util.Coordinate
import com.troystopera.hashi.util.Direction

internal class GenNode(
        value: Int,
        override var coordinate: Coordinate,
        random: Random
) : Comparable<GenNode>, NodeCell(value, coordinate) {

    override var value: Int = value
        private set

    private val uncheckedDirs = Direction.randomOrder(random)
    private val checkedDirs = mutableListOf<Direction>()

    constructor(value: Int, row: Int, col: Int, random: Random) : this(value, Coordinate(row, col), random)

    fun spreadability() = uncheckedDirs.size + checkedDirs.size

    fun isSpreadable() = uncheckedDirs.isNotEmpty() || checkedDirs.isNotEmpty()

    fun hasUnchecked() = uncheckedDirs.isNotEmpty()

    fun setChecked(dir: Direction) {
        uncheckedDirs.remove(dir)
        checkedDirs.add(dir)
    }

    fun remove(dir: Direction) {
        uncheckedDirs.remove(dir)
        checkedDirs.remove(dir)
    }

    fun getNextUnchecked(): Direction {
        if (uncheckedDirs.isEmpty()) throw IllegalStateException("No unchecked Directions remaining in GenNode at $coordinate")
        return uncheckedDirs.first()
    }

    fun getNextChecked(): Direction {
        if (checkedDirs.isEmpty()) throw IllegalStateException("No checked Directions remaining in GenNode at $coordinate")
        return checkedDirs.first()
    }

    operator fun plusAssign(int: Int) {
        value += int
    }

    operator fun minusAssign(int: Int) {
        value -= int
    }

    override fun compareTo(other: GenNode): Int = spreadability().compareTo(other.spreadability())

    override fun hashCode(): Int = coordinate.hashCode() xor (value shl 8)

    override fun equals(other: Any?): Boolean =
            if (other !is GenNode) false
            else (value == other.value) && (coordinate == other.coordinate)

}