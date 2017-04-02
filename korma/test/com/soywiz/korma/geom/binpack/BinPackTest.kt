package com.soywiz.korma.geom.binpack

import com.soywiz.korma.geom.Rectangle
import com.soywiz.korma.geom.Size
import org.junit.Assert
import org.junit.Test

class BinPackTest {
	@Test
	fun name() {
		val factory = BinPacker(100.0, 100.0)
		val result = factory.addBatch(listOf(Size(20, 10), Size(10, 30), Size(100, 20), Size(20, 80)))
		//result.filterNotNull().render().showImageAndWaitExt()
		Assert.assertEquals(
			"[Rectangle(x=20, y=50, width=20, height=10), Rectangle(x=20, y=20, width=10, height=30), Rectangle(x=0, y=0, width=100, height=20), Rectangle(x=0, y=20, width=20, height=80)]",
			result.toString()
		)
	}

	@Test
	fun packSeveral() {
		val packs = BinPacker.packSeveral(10, 10, listOf(Size(10, 10), Size(5, 5), Size(5, 5), Size(5, 5), Size(5, 5), Size(10, 5), Size(5, 10), Size(5, 5)))
		Assert.assertEquals(4, packs.size)
		Assert.assertEquals("[Rectangle(x=0, y=0, width=10, height=10)]", packs[0].rectsStr)
		Assert.assertEquals("[Rectangle(x=0, y=0, width=5, height=5), Rectangle(x=0, y=5, width=5, height=5), Rectangle(x=5, y=0, width=5, height=5), Rectangle(x=5, y=5, width=5, height=5)]", packs[1].rectsStr)
		Assert.assertEquals("[Rectangle(x=0, y=0, width=10, height=5)]", packs[2].rectsStr)
		Assert.assertEquals("[Rectangle(x=0, y=0, width=5, height=10), Rectangle(x=5, y=0, width=5, height=5)]", packs[3].rectsStr)
	}
}