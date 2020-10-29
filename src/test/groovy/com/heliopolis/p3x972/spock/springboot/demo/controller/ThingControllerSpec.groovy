package com.heliopolis.p3x972.spock.springboot.demo.controller

import com.heliopolis.p3x972.spock.springboot.demo.BaseSpec
import com.heliopolis.p3x972.spock.springboot.demo.domain.ThingAndStuff
import com.heliopolis.p3x972.spock.springboot.demo.entity.Thing
import com.heliopolis.p3x972.spock.springboot.demo.service.ThingService
import spock.lang.Unroll

import java.time.LocalDateTime

@SuppressWarnings('GroovyAccessibility')
class ThingControllerSpec extends BaseSpec {

    ThingService mockService
    ThingController controller

    def setup() {
        mockService = Mock()
        controller = new ThingController(mockService)
    }

    @Unroll
    def 'getThing(#i)'() {
        given: 'any particular response from the service'
        mockService.get(i) >> thing

        when:
        def result = controller.getThing(i)

        then: 'the value from service should be simply passed through the controller'
        result == thing

        where:
        i    | thing
        0    | Mock(ThingAndStuff)
        1    | Mock(ThingAndStuff)
        10   | Mock(ThingAndStuff)
        999  | Mock(ThingAndStuff)
        -999 | Mock(ThingAndStuff)
    }

    def 'getThing() throws exception'() {
        given: 'an exception is thrown by the service'
        def ex = new RuntimeException()
        mockService.get(_ as Integer) >> { throw ex }

        when:
        controller.getThing(1)

        then: 'expect the exception to be thrown here as well'
        def result = thrown(RuntimeException)
        result == ex
    }

    def 'saveThing() - handling both happy-path and exception in the same test'() {
        given:
        def ex = new RuntimeException()
        def thing = Mock(ThingAndStuff)
        //noinspection GroovyAssignabilityCheck
        mockService.add({ Thing thing1 -> 'foobar' == thing1.name}) >> { throw ex }
        mockService.add(_ as Thing) >> thing

        when:
        controller.saveThing(new Thing(1, 'foobar', LocalDateTime.now()))

        then: 'an exception thrown by the service should be rethrown'
        def thrown = thrown(RuntimeException)
        thrown == ex
        // a failure in this "then" will short-circuit this test

        when:
        def result = controller.saveThing(new Thing(2, 'not foobar', LocalDateTime.now()))

        then: 'the result from the service should be returned'
        result == thing
        // if the first "then" was short-circuited, then this "then" test will not be attempted
    }
}
