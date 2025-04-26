package com.heliopolis.p3x972.spock.springboot.demo.service

import com.heliopolis.p3x972.spock.springboot.demo.domain.Stuff
import org.springframework.stereotype.Service


@Service
class StuffService {
    fun get(id: Int): List<Stuff> {
        return when (id) {
            0 -> mutableListOf(createStuff("sonic screwdriver"), createStuff("The Doctor"), createStuff("Bad Wolf"))
            1 -> mutableListOf(createStuff("Jedi"), createStuff("Sith"))
            2 -> mutableListOf(createStuff("Never give up!"), createStuff("Never surrender!"))
            3 -> mutableListOf(createStuff("Federation"), createStuff("Romulan Star Empire"))
            else -> emptyList<Stuff>().toMutableList()
        }
    }

    fun createStuff(value: String): Stuff {
        return Stuff(value)
    }
}