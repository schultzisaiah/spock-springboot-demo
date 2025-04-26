package com.heliopolis.p3x972.spock.springboot.demo.service


import com.heliopolis.p3x972.spock.springboot.demo.config.FeatureToggle
import com.heliopolis.p3x972.spock.springboot.demo.domain.Stuff
import com.heliopolis.p3x972.spock.springboot.demo.entity.Thing
import com.heliopolis.p3x972.spock.springboot.demo.repository.ThingRepo
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException
import org.togglz.junit5.AllEnabled
import org.togglz.testing.TestFeatureManager
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

import static java.time.LocalDateTime.now

@SuppressWarnings('GroovyAccessibility')
class ThingServiceSpec extends Specification {

    // This spec is set up as INTEGRATION style tests (ie: dependencies are "real" and not mocked)

    ThingRepo thingRepo
    StuffService stuffService

    ThingService service

    def setup() {
        thingRepo = Spy(ThingRepo)
        stuffService = Spy(StuffService)
        service = new ThingService(thingRepo, stuffService, "default")
    }

    @Unroll
    def 'get(#i) - #name: #listOfStuff'() {
        when:
        def result = service.get(i)

        then:
        1 * stuffService.get(i)
        result.id == i
        result.name == name
        !result.updateDate.isBefore(updateDate)
        result.listOfStuff == listOfStuff

        where:
        i | name       | updateDate             | listOfStuff
        0 | 'TARDIS'   | now().minusDays(2)     | [new Stuff("sonic screwdriver"), new Stuff("The Doctor"), new Stuff("Bad Wolf")]
        1 | 'Holocron' | now().minusYears(2000) | [new Stuff("Jedi"), new Stuff("Sith")]
        2 | 'Omega 13' | now().minusYears(10)   | [new Stuff("Never give up!"), new Stuff("Never surrender!")]
    }

    @Unroll
    def 'get() - does not exist in #env!'() {
        given:
        service.env = env

        when:
        service.get(100)

        then:
        def ex = thrown(HttpClientErrorException)
        ex.statusCode == HttpStatus.NOT_FOUND
        ex.message.contains("in ${env}")

        where:
        env << ['dev', 'qa', 'prod']
    }

    @Unroll
    def 'add(#newThing)'() {
        given:
        def now = now()

        when:
        def result = service.add(newThing)

        then:
        result.id == id
        result.name == name
        !result.updateDate.isBefore(now)
        result.listOfStuff == listOfStuff

        then:
        1 * thingRepo.delete(newThing)

        then:
        1 * thingRepo.deleteById(id)

        then:
        1 * stuffService.get(id)

        where:
        newThing                                                 | id | name     | listOfStuff
        new Thing(3, "Vulcan", LocalDateTime.now().minusDays(2)) | 3  | "Vulcan" | [new Stuff("Federation"), new Stuff("Romulan Star Empire")]
        new Thing(4, "Dune", LocalDateTime.now().minusDays(2))   | 4  | "Dune"   | []
    }


    @AllEnabled(FeatureToggle.class)
    def 'add existing - toggle off!'(TestFeatureManager featureManager) {
        given:
        featureManager.disable(FeatureToggle.ALLOW_UPDATES)

        when:
        service.add(new Thing(0, null, null))

        then:
        def ex = thrown(HttpClientErrorException)
        ex.statusCode == HttpStatus.CONFLICT
    }

    @AllEnabled(FeatureToggle.class)
    def 'add existing - toggle on!'() {
        given:
        def now = now()

        when:
        def result = service.add(new Thing(0, 'TARDIS!!!', LocalDateTime.now().minusYears(10)))

        then:
        result.id == 0
        result.name == 'TARDIS!!!'
        result.listOfStuff == [new Stuff("sonic screwdriver"), new Stuff("The Doctor"), new Stuff("Bad Wolf")]
        !result.updateDate.isBefore(now)
    }
}
