package com.soywiz.korma.math

import com.soywiz.korma.Vector2
import com.soywiz.korma.geom.PointInt
import com.soywiz.korma.interpolation.Interpolable

header object NativeMath {
	fun round(v: Double): Double
	fun ceil(v: Double): Double
	fun floor(v: Double): Double

	fun pow(b: Double, e: Double): Double

	fun log(v: Double): Double

	fun acos(v: Double): Double
	fun asin(v: Double): Double
	fun atan(v: Double): Double
	fun atan2(y: Double, x: Double): Double

	fun cos(v: Double): Double
	fun sin(v: Double): Double
	fun tan(v: Double): Double
	fun sqrt(v: Double): Double

	fun intBitsToFloat(v: Int): Float
	fun longBitsToDouble(v: Long): Double
	fun floatToIntBits(v: Float): Int
	fun doubleToLongBits(v: Double): Long
}

object Math {
	const val E = 2.7182818284590452354
	const val PI = 3.14159265358979323846
	private const val DEGREES_TO_RADIANS = 0.017453292519943295
	private const val RADIANS_TO_DEGREES = 57.29577951308232

	fun round(v: Double): Double = NativeMath.round(v)
	fun ceil(v: Double): Double = NativeMath.ceil(v)
	fun floor(v: Double): Double = NativeMath.floor(v)

	fun divCeil(x: Int, y: Int): Int = 1 + ((x - 1) / y)

	fun cos(value: Float): Float = NativeMath.cos(value.toDouble()).toFloat()
	fun sin(value: Float): Float = NativeMath.sin(value.toDouble()).toFloat()
	fun tan(value: Float): Float = NativeMath.tan(value.toDouble()).toFloat()
	fun sqrt(value: Float): Float = NativeMath.sqrt(value.toDouble()).toFloat()

	fun cos(value: Double): Double = NativeMath.cos(value)
	fun sin(value: Double): Double = NativeMath.sin(value)
	fun tan(value: Double): Double = NativeMath.tan(value)
	fun sqrt(value: Double): Double = NativeMath.sqrt(value)
	fun cbrt(value: Double): Double = NativeMath.sqrt(value)

	fun acos(value: Double): Double = NativeMath.acos(value)
	fun asin(value: Double): Double = NativeMath.asin(value)
	fun atan(value: Double): Double = NativeMath.atan(value)

	fun atan2(y: Double, x: Double): Double = NativeMath.atan2(y, x)

	fun len(a: Double, b: Double) = NativeMath.sqrt(a * a + b * b)
	fun reinterpretIntFloat(value: Int): Float = NativeMath.intBitsToFloat(value)

	fun interpolate(min: Int, max: Int, ratio: Double): Int = min + ((max - min) * ratio).toInt()
	fun interpolate(min: Long, max: Long, ratio: Double) = min + ((max - min) * ratio).toLong()

	fun <T : Interpolable<T>> interpolate(min: T, max: T, ratio: Double): T = min.interpolateWith(max, ratio)

	fun interpolateAny(min: Any, max: Any, ratio: Double): Any {
		return when (min) {
			is Int -> interpolate(min, max as Int, ratio)
			is Long -> interpolate(min, max as Long, ratio)
			is Double -> interpolate(min, max as Double, ratio)
			is Vector2 -> min.setToInterpolated(min, max as Vector2, ratio)
			else -> throw RuntimeException("Unsupported interpolate with $min")
		}
	}

	fun min(a: Int, b: Int): Int = if (a < b) a else b
	fun max(a: Int, b: Int): Int = if (a > b) a else b

	fun min(a: Double, b: Double): Double = if (a < b) a else b
	fun max(a: Double, b: Double): Double = if (a > b) a else b

	fun min(a: Double, b: Double, c: Double, d: Double): Double = Math.min(Math.min(a, b), Math.min(c, d))
	fun max(a: Double, b: Double, c: Double, d: Double): Double = Math.max(Math.max(a, b), Math.max(c, d))

	fun clamp(v: Long, min: Long, max: Long): Long = if (v < min) min else if (v > max) max else v
	fun clamp(v: Int, min: Int, max: Int): Int = if (v < min) min else if (v > max) max else v
	fun clamp(value: Double, min: Double, max: Double): Double = if (value < min) min else if (value > max) max else value
	fun clampSpecial(value: Double, min: Double, max: Double): Double = if (max >= min) clamp(value, min, max) else value

	fun clamp(value: Float, min: Float, max: Float): Float = if (value < min) min else if (value > max) max else value
	fun clampInt(value: Int, min: Int, max: Int): Int = if (value < min) min else if (value > max) max else value
	fun clampf255(v: Double): Int = if (v < 0.0) 0 else if (v > 1.0) 255 else (v * 255).toInt()
	fun clampf01(v: Double): Double = if (v < 0.0) 0.0 else if (v > 1.0) 1.0 else v
	fun clampn255(v: Int): Int = if (v < -255) -255 else if (v > 255) 255 else v
	fun clamp255(v: Int): Int = if (v < 0) 0 else if (v > 255) 255 else v

	fun distance(a: Double, b: Double): Double = Math.abs(a - b)
	fun distance(x1: Double, y1: Double, x2: Double, y2: Double): Double = Math.hypot(x1 - x2, y1 - y2)
	fun distance(x1: Int, y1: Int, x2: Int, y2: Int): Double = Math.hypot((x1 - x2).toDouble(), (y1 - y2).toDouble())
	fun distance(a: Vector2, b: Vector2): Double = distance(a.x, a.y, b.x, b.y)
	fun distance(a: PointInt, b: PointInt): Double = distance(a.x, a.y, b.x, b.y)

	fun smoothstep(edge0: Double, edge1: Double, step: Double): Double {
		val step2 = clamp((step - edge0) / (edge1 - edge0), 0.0, 1.0)
		return step2 * step2 * (3 - 2 * step2)
	}

	fun interpolate(v0: Double, v1: Double, step: Double): Double = v0 * (1 - step) + v1 * step

	fun modUnsigned(num: Double, den: Double): Double {
		var result: Double = (num % den)
		if (result < 0) result += den
		return result
	}

	fun between(value: Double, min: Double, max: Double): Boolean = (value >= min) && (value <= max)

	fun convertRange(value: Double, minSrc: Double, maxSrc: Double, minDst: Double, maxDst: Double): Double = (((value - minSrc) / (maxSrc - minSrc)) * (maxDst - minDst)) + minDst

	fun sign(x: Double): Int = if (x < 0) -1 else if (x > 0) +1 else 0
	fun signNonZeroM1(x: Double): Int = if (x <= 0) -1 else +1
	fun signNonZeroP1(x: Double): Int = if (x >= 0) +1 else -1

	fun multiplyIntegerUnsigned(a: Int, b: Int) = (a * b) or 0
	fun multiplyIntegerSigned(a: Int, b: Int): Int = (a * b) or 0
	fun divideIntegerUnsigned(a: Int, b: Int): Int = (a / b) or 0
	fun divideIntegerSigned(a: Int, b: Int): Int = (a / b) or 0
	fun hypot(x: Double, y: Double): Double = Math.sqrt(x * x + y * y)
	fun hypotNoSqrt(x: Double, y: Double): Double = (x * x + y * y)

	fun roundDecimalPlaces(value: Double, places: Int): Double {
		val placesFactor: Double = NativeMath.pow(10.0, places.toDouble())
		return NativeMath.round(value * placesFactor) / placesFactor
	}

	fun isEquivalent(a: Double, b: Double, epsilon: Double = 0.0001): Boolean = (a - epsilon < b) && (a + epsilon > b)
	fun packUintFast(r: Int, g: Int, b: Int, a: Int): Int = (a shl 24) or (b shl 16) or (g shl 8) or (r shl 0)
	fun pack4fUint(r: Double, g: Double, b: Double, a: Double): Int = packUintFast(clampf255(r), clampf255(g), clampf255(b), clampf255(a))
	fun log2(v: Int): Int = (NativeMath.log(v.toDouble()) / NativeMath.log(2.0)).toInt()

	fun distanceXY(x1: Double, y1: Double, x2: Double, y2: Double): Double = hypot(x1 - x2, y1 - y2);
	fun distancePoint(a: Vector2, b: Vector2): Double = distanceXY(a.x, a.y, b.x, b.y);

	fun abs(v: Int): Int = if (v < 0) -v else v
	fun abs(v: Long): Long = if (v < 0) -v else v
	fun abs(v: Float): Float = if (v < 0) -v else v
	fun abs(v: Double): Double = if (v < 0) -v else v
}