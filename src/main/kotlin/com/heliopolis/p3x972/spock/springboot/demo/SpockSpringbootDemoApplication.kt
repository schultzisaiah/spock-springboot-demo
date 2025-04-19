package com.heliopolis.p3x972.spock.springboot.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpockSpringbootDemoApplication

fun main(args: Array<String>) {
    runApplication<SpockSpringbootDemoApplication>(*args)
}
