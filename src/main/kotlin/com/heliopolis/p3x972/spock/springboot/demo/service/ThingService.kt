package com.heliopolis.p3x972.spock.springboot.demo.service

import com.heliopolis.p3x972.spock.springboot.demo.config.FeatureToggle
import com.heliopolis.p3x972.spock.springboot.demo.domain.ThingAndStuff
import com.heliopolis.p3x972.spock.springboot.demo.entity.Thing
import com.heliopolis.p3x972.spock.springboot.demo.repository.ThingRepo
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException


@Service
class ThingService(
    private val thingRepo: ThingRepo,
    private val stuffService: StuffService,
    @Value("\${app.env:}") private var env: String
) {

    fun get(id: Int): ThingAndStuff {
        return thingRepo.findById(id)
            .map { t: Thing -> ThingAndStuff(t, stuffService.get(t.id)) }
            .orElseThrow {
                HttpClientErrorException(
                    HttpStatus.NOT_FOUND,
                    "Thing with id $id does not exist in $env"
                )
            }
    }

    fun add(newThing: Thing): ThingAndStuff {
        if (!FeatureToggle.ALLOW_UPDATES.isActive && thingRepo.existsById(newThing.id)) {
            throw HttpClientErrorException(
                HttpStatus.CONFLICT, "Thing with id ${newThing.id} already exists in $env!"
            )
        }
        return ThingAndStuff(thingRepo.save(newThing), stuffService.get(newThing.id))
    }
}