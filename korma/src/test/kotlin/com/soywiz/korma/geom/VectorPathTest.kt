package com.soywiz.korma.geom

import org.junit.Assert
import org.junit.Test

class VectorPathTest {
	@Test
	fun testSimpleSquare() {
		val g = VectorPath()
		g.moveTo(0, 0)
		g.lineTo(100, 0)
		g.lineTo(100, 100)
		g.lineTo(0, 100)
		g.close()

		Assert.assertEquals(true, g.containsPoint(50, 50))
		Assert.assertEquals(false, g.containsPoint(150, 50))
		Assert.assertEquals(Rectangle(0, 0, 100, 100), g.getBounds())
	}

	@Test
	fun testCircle() {
		val g = VectorPath()
		g.circle(0.0, 0.0, 100.0)
		Assert.assertEquals(true, g.containsPoint(0, 0))
		Assert.assertEquals(false, g.containsPoint(120, 0))
		Assert.assertEquals(false, g.containsPoint(-100, -100))
		Assert.assertEquals(true, g.containsPoint(64, 64))
		Assert.assertEquals(false, g.containsPoint(78, 78))
	}

	@Test
	fun testSquareWithHole() {
		val g = VectorPath()
		g.moveTo(0, 0)
		g.lineTo(100, 0)
		g.lineTo(100, 100)
		g.lineTo(0, 100)
		g.close()

		g.moveTo(75, 25)
		g.lineTo(25, 25)
		g.lineTo(25, 75)
		g.lineTo(75, 75)
		g.close()

		Assert.assertEquals(false, g.containsPoint(50, 50))
		Assert.assertEquals(false, g.containsPoint(150, 50))
		Assert.assertEquals(true, g.containsPoint(20, 50))
		Assert.assertEquals(Rectangle(0, 0, 100, 100), g.getBounds())
		//g.filled(Context2d.Color(Colors.RED)).raster().showImageAndWaitExt()
	}
}