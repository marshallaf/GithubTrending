package com.marshallaf.mobileui.test.factory

import java.util.*
import java.util.concurrent.ThreadLocalRandom

object TestDataFactory {

  fun randomString() = UUID.randomUUID().toString()

  fun randomInt() = ThreadLocalRandom.current().nextInt(0, 1001)

  fun randomLong() = randomInt().toLong()

  fun randomBoolean() = ThreadLocalRandom.current().nextBoolean()
}
