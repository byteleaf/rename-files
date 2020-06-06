package de.byteleaf.renamefiles.control.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.info.BuildProperties
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

@ContextConfiguration(classes = [PathLocationService.class, BuildProperties.class])
@TestPropertySource(properties = "root-folder.name=test-root")
class PathLocationServiceSpec extends Specification {
    @Autowired
    PathLocationService pathLocationService

    def 'getBaseFolder'() {
        expect:
        pathLocationService.getBaseFolder().name == 'test-root'
    }

//    def 'findFolder'() {
//        expect:
//        currentFile.parentFile = parent
//        pathLocationService.findFolder(currentFile, targetFolderSuffixes).name == result
//        where:
//        currentFile                       | targetFolderSuffixes  || result
//        new File('test123', 'sample.file')  | ['test']              || 'test123'
//        new File('test123', 'sample.file')  | ['bla', '123']        || 'test123'
//    }
}