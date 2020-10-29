package com.heliopolis.p3x972.spock.springboot.demo

import com.heliopolis.p3x972.spock.springboot.demo.config.Features
import org.junit.Rule
import org.togglz.junit.TogglzRule
import spock.lang.Specification

abstract class BaseSpec extends Specification {
    @Rule
    TogglzRule togglzRule = TogglzRule.allEnabled(Features.class)

    void setEnabled(boolean enabled, Features toggle) {
        if (enabled) {
            togglzRule.enable(toggle)
        } else {
            togglzRule.disable(toggle)
        }
    }
}
