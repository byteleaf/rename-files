package de.byteleaf.renamefiles.control.service


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = DateService)
class DateServiceSpec extends Specification {

    @Autowired
    private DateService dateService

    def 'isValidDate'() {
        expect:
        dateService.isValidDate(date, "yyyy-MM-dd HH-mm-ss") == result
        where:
        date                    || result
        "2020-10-01 23-01-13"   || true
    }
}
