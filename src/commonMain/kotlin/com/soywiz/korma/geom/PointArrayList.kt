package com.soywiz.korma.geom

import com.soywiz.kds.*
import com.soywiz.korma.algo.*
import kotlin.math.*

interface IPointArrayList {
    val size: Int
    fun getX(index: Int): Double
    fun getY(index: Int): Double
}

class PointArrayList(capacity: Int = 7) : IPointArrayList {
    private val xList = DoubleArrayList(capacity)
    private val yList = DoubleArrayList(capacity)
    override val size get() = xList.size

    fun isEmpty() = size == 0
    fun isNotEmpty() = size != 0

    companion object {
        operator fun invoke(capacity: Int = 7, callback: PointArrayList.() -> Unit): PointArrayList = PointArrayList(capacity).apply(callback)
        operator fun invoke(points: List<IPoint>): PointArrayList = PointArrayList(points.size) {
            for (n in points.indices) add(points[n].x, points[n].y)
        }
    }

    fun add(x: Double, y: Double) = this.apply {
        xList += x
        yList += y
    }

    override fun getX(index: Int) = xList[index]
    override fun getY(index: Int) = yList[index]

    fun setX(index: Int, x: Double) = run { xList[index] = x }
    fun setY(index: Int, y: Double) = run { yList[index] = y }
    fun setXY(index: Int, x: Double, y: Double) {
        xList[index] = x
        yList[index] = y
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append('[')
        for (n in 0 until size) {
            val x = getX(n)
            val y = getY(n)
            if (n != 0) {
                sb.append(", ")
            }
            sb.append('(')
            if (x == round(x)) sb.append(x.toInt()) else sb.append(x)
            sb.append(", ")
            if (y == round(y)) sb.append(y.toInt()) else sb.append(y)
            sb.append(')')
        }
        sb.append(']')
        return sb.toString()
    }

    fun swap(indexA: Int, indexB: Int) {
        xList.swap(indexA, indexB)
        yList.swap(indexA, indexB)
    }

    fun sort() {
        genericSort(this, 0, this.size - 1, PointSortOpts)
    }

    object PointSortOpts : SortOps<PointArrayList>() {
        override fun compare(p: PointArrayList, l: Int, r: Int): Int {
            return Point.compare(p.getX(l), p.getY(l), p.getX(r), p.getY(r))
        }

        override fun swap(subject: PointArrayList, indexL: Int, indexR: Int) {
            subject.swap(indexL, indexR)
        }
    }
}

inline fun PointArrayList.add(x: Number, y: Number) = add(x.toDouble(), y.toDouble())
fun PointArrayList.add(p: IPoint) = add(p.x, p.y)

fun PointArrayList.add(other: IPointArrayList) = this.apply {
    for (n in 0 until other.size) add(other.getX(n), other.getY(n))
}

inline fun PointArrayList.setX(index: Int, x: Number) = setX(index, x.toDouble())
inline fun PointArrayList.setY(index: Int, y: Number) = setY(index, y.toDouble())
inline fun PointArrayList.setXY(index: Int, x: Number, y: Number) = setXY(index, x.toDouble(), y.toDouble())

private fun DoubleArrayList.swap(indexA: Int, indexB: Int) {
    val tmp = this[indexA]
    this[indexA] = this[indexB]
    this[indexB] = tmp
}

fun IPointArrayList.getPoint(index: Int): Point = Point(getX(index), getY(index))
fun IPointArrayList.getIPoint(index: Int): IPoint = IPoint(getX(index), getY(index))
fun IPointArrayList.toPoints(): List<Point> = (0 until size).map { getPoint(it) }
fun IPointArrayList.toIPoints(): List<IPoint> = (0 until size).map { getIPoint(it) }
fun IPointArrayList.contains(x: Double, y: Double): Boolean {
    for (n in 0 until size) if (getX(n) == x && getY(n) == y) return true
    return false
}

inline fun IPointArrayList.contains(x: Number, y: Number): Boolean = contains(x.toDouble(), y.toDouble())