package com.amondel2.techtalk

import org.grails.testing.GrailsUnitTest
import org.openqa.selenium.remote.ErrorCodes
import spock.lang.Specification

class BaseFmServiceSpec extends Specification implements GrailsUnitTest {

    def setup() {
    }

    def cleanup() {
    }

    void "Basic Test"() {

        given:
          def cmd = new EmployeeMCommand()


        when:
        getErrorList(cmd)

        then:
        cmd.hasErrors() == true

    }
}