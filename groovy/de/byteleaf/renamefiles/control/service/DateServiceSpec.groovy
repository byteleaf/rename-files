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
        "blablbala"             || false
        "2020-3-3 1-1-1"        || true
        "2020-3-3 1-1"          || false
    }

    def 'parseDate'() {
        expect:
        dateService.parseDate(date, "yyyy-MM-dd") == result
        where:
        date           || result
        "2020-10-01"   || new Date(120, 9, 1)
    }

    def 'formatDate'() {
        expect:
        dateService.formatDate(date, "yyyy-MM-dd HH-mm-ss") == result
        where:
        date                           || result
        new Date(120, 9, 1, 1, 2, 3)   || "2020-10-01 01-02-03"
    }
}
