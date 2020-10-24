package de.byteleaf.renamefiles.control.service

import de.byteleaf.renamefiles.constant.FileType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import java.nio.file.Paths

@ContextConfiguration(classes = [FileTypeService.class])
class FileTypeServiceSpec extends Specification {
    @Autowired
    private FileTypeService fileTypeService

    def 'getFileType'() {
        expect:
        fileTypeService.getFileType(path) == result
        where:
        path                       || result
        Paths.get('./test.jpg')    || FileType.JPG
        Paths.get('./test')        || null
        Paths.get('/')             || null
        Paths.get('test.unknown')  || null
    }

    def 'isFileTypeSupported'() {
        expect:
        fileTypeService.isFileTypeSupported(path) == result
        where:
        path                       || result
        Paths.get('./test.jpg')    || true
        Paths.get('test.unknown')  || false
    }

    def 'getFileEnding'() {
        expect:
        fileTypeService.getFileEnding(path) == result
        where:
        path                       || result
        Paths.get('./test.jpg')    || '.jpg'
        Paths.get('test.unknown')  || '.unknown'
    }
}