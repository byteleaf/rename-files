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
    private PathLocationService pathLocationService

    def 'getBaseFolder'() {
        expect: 'The base folder was found, no exception!'
        pathLocationService.getBaseFolder().name == 'rename-files'
    }

    def 'getFolder'() {
        expect:
        pathLocationService.getFolder(relativePath).absolutePath.endsWith(resultSuffix)
        where:
        relativePath        || resultSuffix
        'test'              || 'rename-files\\test'
        '/test/test-sub'    || 'rename-files\\test\\test-sub'
        '\\test/test-sub'   || 'rename-files\\test\\test-sub'
    }

    def 'getFolder, expect exception, folder is not existing'() {
        when:
        pathLocationService.getFolder('unknownfolder')
        then:
        thrown(RuntimeException)
    }

    def 'getFolder, expect exception, its a file'() {
        when:
        pathLocationService.getFolder('test/readme.md')
        then:
        thrown(RuntimeException)
    }
}