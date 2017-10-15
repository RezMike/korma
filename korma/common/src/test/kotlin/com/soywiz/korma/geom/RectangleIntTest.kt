package com.soywiz.korma.geom

import org.junit.Test
import kotlin.test.assertEquals

class RectangleIntTest {
	@Test
	fun name() {
		assertEquals(SizeInt(25, 100), SizeInt(50, 200).fitTo(container = SizeInt(100, 100)))
		assertEquals(SizeInt(50, 200), SizeInt(50, 200).fitTo(container = SizeInt(100, 200)))
	}
}