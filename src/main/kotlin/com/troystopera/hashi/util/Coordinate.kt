package com.troystopera.hashi.util

data class Coordinate(val row: Int, val col: Int) {

    override fun hashCode(): Int = row or (col shl 16)

    override fun toString(): String = "($row,$col)"

    override fun equals(other: Any?): Boolean {
        if (other !is Coordinate) return false
        return (row == other.row) && (col == other.col)
    }

}