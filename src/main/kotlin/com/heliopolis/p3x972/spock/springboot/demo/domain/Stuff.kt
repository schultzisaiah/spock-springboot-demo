package com.heliopolis.p3x972.spock.springboot.demo.domain

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include

@JsonInclude(Include.NON_EMPTY)
data class Stuff(var value: String)
