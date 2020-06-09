package de.byteleaf.renamefiles.control.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.info.BuildProperties
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

@ContextConfiguration(classes = [PathLocationService.class, BuildProperties.class])
@TestPropertySource(properties = "root-folder.name=rename-files")
class PathLocationServiceSpec extends Specification {
    @Autowired
    PathLocationService pathLocationService

    def 'getBaseFolder'() {
        expect: 'The base folder was found, no exception!'
        pathLocationService.getBaseFolder().name == 'rename-files'
    }

    def 'getFolder'() {
        expect:
        pathLocationService.getFolder(relativePath).absolutePath.endsWith(resultSuffix)
        where:
        relativePath   || resultSuffix
        'src'          || 'rename-files\\src'
        '/src/main'    || 'rename-files\\src\\main'
        '\\src/main'   || 'rename-files\\src\\main'
    }

    def 'getFolder, expect exception, folder is not existing'() {
        when:
        pathLocationService.getFolder('unknownfolder')
        then:
        thrown(RuntimeException)
    }

    def 'getFolder, expect exception, its a file'() {
        when:
        pathLocationService.getFolder('pom.xml')
        then:
        thrown(RuntimeException)
    }
}