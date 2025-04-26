package com.heliopolis.p3x972.spock.springboot.demo.domain

import com.heliopolis.p3x972.spock.springboot.demo.entity.Thing


class ThingAndStuff(thing: Thing, private val listOfStuff: List<Stuff>) :
    BaseThing(thing.id, thing.name, thing.updateDate)