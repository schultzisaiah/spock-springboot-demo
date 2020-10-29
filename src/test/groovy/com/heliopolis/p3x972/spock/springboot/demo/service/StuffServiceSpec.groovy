package com.heliopolis.p3x972.spock.springboot.demo.service

import com.heliopolis.p3x972.spock.springboot.demo.BaseSpec
import spock.lang.Unroll

@SuppressWarnings('GroovyAccessibility')
class StuffServiceSpec extends BaseSpec {
    StuffService service

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
}
