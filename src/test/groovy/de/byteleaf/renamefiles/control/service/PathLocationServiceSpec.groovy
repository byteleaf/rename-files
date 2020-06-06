package de.byteleaf.renamefiles.control.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.info.BuildProperties
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

//@ContextConfiguration(classes = [PathLocationService.class, BuildProperties.class])
class PathLocationServiceSpec extends Specification {
//    @Autowired
//    PathLocationService pathLocationService

    def "when context is loaded then all expected beans are created"() {
        expect: "the SplitSlotsService is created"
    //    pathLocationService
    }
}