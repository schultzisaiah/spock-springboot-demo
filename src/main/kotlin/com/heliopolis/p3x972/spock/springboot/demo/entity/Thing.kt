package com.heliopolis.p3x972.spock.springboot.demo.entity

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.heliopolis.p3x972.spock.springboot.demo.domain.BaseThing
import java.time.LocalDateTime

@JsonInclude(Include.NON_EMPTY)
data class Thing(
    override var id: Int,
    override var name: String? = null,
    override var updateDate: LocalDateTime? = null
): BaseThing(id, name, updateDate)
