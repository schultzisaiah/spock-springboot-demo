package com.heliopolis.p3x972.spock.springboot.demo.service


import com.heliopolis.p3x972.spock.springboot.demo.config.FeatureToggle
import org.togglz.junit5.AllEnabled
import spock.lang.Specification
import spock.lang.Unroll

@SuppressWarnings('GroovyAccessibility')
class StuffServiceSpec extends Specification {
    StuffService service

    @AllEnabled(FeatureToggle.class)
    def setup() {
        service = new StuffService()
    }

    @Unroll
    def 'test create Stuff entity'() {
        when:
        def result = service.createStuff(value)

        then:
        result.value == value

        where:
        value << ['one', 'car', 'foobar']
    }

    @Unroll
    def 'test create Stuff entity - FAILURE DEMO'() {
        when:
        def result = service.createStuff(value + '!')

        then:
        result.value == value

        where:
        value << ['one', 'car', 'foobar']
    }
}
