package de.byteleaf.renamefiles.control.service


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

@ContextConfiguration(classes = [FileNameService.class, CreationDateService.class, PathLocationService.class])
@TestPropertySource(properties = "root-folder.name=rename-files")
class FileNameServiceSpec extends Specification {
    @Autowired
    private FileNameService fileNameService
    @Autowired
    private CreationDateService creationDateService
    @Autowired
    private PathLocationService pathLocationService

//    def setupSpec() {
//        Mockito.when(creationDateService.getCreationDate(Mockito.any())).thenReturn(new Date(2020, 4, 7, 2, 5, 9))
//    }

    def 'generateName integration test'() {
        expect:
        fileNameService.generateName(pathLocationService.getFile(path).toPath(),"yyyy-MM-dd HH-mm-ss", "Test") == result
        where:
        path                                       || result
        'test/test-sub/with-exif-tag-datetime.jpg' || "2015-08-23 18:40:17 Test"
    }
}
