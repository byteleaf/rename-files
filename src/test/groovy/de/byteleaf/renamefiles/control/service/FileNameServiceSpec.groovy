package de.byteleaf.renamefiles.control.service


import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = [FileNameService.class])
class FileNameServiceSpec extends Specification {
//    @Autowired
//    private FileNameService fileNameService
//    @Mock
//    private CreationDateService creationDateService
//
//    def setupSpec() {
//        Mockito.when(creationDateService.getCreationDate(Mockito.any())).thenReturn(new Date(2020, 4, 7, 2, 5, 9))
//    }

//    def 'getFileType'() {
//        expect:
//        fileTypeService.getFileType(path) == result
//        where:
//        path                       || result
//        Paths.get('./test.jpg')    || FileType.JPG
//        Paths.get('./test')        || null
//        Paths.get('/')             || null
//        Paths.get('test.unknown')  || null
//    }
}
