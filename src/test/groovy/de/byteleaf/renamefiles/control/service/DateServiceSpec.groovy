package de.byteleaf.renamefiles.control.service

import de.byteleaf.renamefiles.constant.FileType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import java.nio.file.Paths

@ContextConfiguration(classes = DateService)
class DateServiceSpec {

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
