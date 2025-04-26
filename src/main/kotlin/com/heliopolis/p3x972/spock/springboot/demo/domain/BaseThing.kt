package com.heliopolis.p3x972.spock.springboot.demo.domain

import java.time.LocalDateTime

abstract class BaseThing(
    open var id: Int,
    open var name: String? = null,
    open var updateDate: LocalDateTime? = null
)