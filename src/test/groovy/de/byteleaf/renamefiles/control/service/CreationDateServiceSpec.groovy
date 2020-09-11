package de.byteleaf.renamefiles.control.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.info.BuildProperties
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

import java.text.SimpleDateFormat

@ContextConfiguration(classes = [CreationDateService.class, PathLocationService.class, BuildProperties.class])
@TestPropertySource(properties = "root-folder.name=rename-files")
class CreationDateServiceSpec extends Specification {

    @Autowired
    private CreationDateService creationDateService
    @Autowired
    private PathLocationService pathLocationService

    def 'getCreationDate'() {
        expect:
        // TODO Timezone problem -> should be 2015-08-23 20:40:17
        creationDateService.getCreationDate(pathLocationService.getFile(path).toPath()).equals(result)
        where:
        path                                         || result
        'test/test-sub/with-exif-tag-datetime.jpg'   || new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2015-08-23 20:40:17")
    }
}
