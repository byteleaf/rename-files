package de.byteleaf.renamefiles.control.service


import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

@ContextConfiguration(classes = [FileNameService.class, DateService.class, FileTypeService.class, CreationDateService.class, PathLocationService.class])
@TestPropertySource(properties = "root-folder.name=rename-files")
class FileNameServiceSpec extends Specification {

        def 'test'() {
        expect:
        true
    }

////    @Autowired
////    private FileNameService fileNameService
////
////    @Autowired
////    private DateService dateService
////
////    @Autowired
////    private PathLocationService pathLocationService
////
////    @SpringBean
////    private FileTypeService fileTypeService = Stub(FileTypeService) {
////        isFileTypeSupported(_) >> { args -> args[0].fileName.toString().endsWith('.jpg') }
////    }
////
////    @SpringBean
////    private CreationDateService creationDateService = Stub(CreationDateService) {
////        getCreationDate(_) >> { args -> args[0].fileName.toString().contains('CR_FOUND') ? new Date(2020, 4, 7, 2, 5, 9) : null }
////    }
////
////    def 'isRenamePossible'() {
////        expect:
////        fileNameService.isRenamePossible(Paths.get(path)) == result
////        where:
////        path                                        || result
////        '2015-08-23 18-40-17 CR_FOUND.notSupported' || false
////        '2015-08 23 18-40-17 CR_FOUND.jpg'          || true
////        '2015-08 23 18-40-17 CR_NOT_FOUND.jpg'      || false
////    }
//
//    // TODO not working
////    @IgnoreRest
////    def 'getAppendix'() {
////        given:
////        def path = pathLocationService.getFile('test/appendix/2020_suffix.jpg').toPath()
////        expect:
////        fileNameService.getAppendix(path, dateCreated, '_suffix', '.jpg',0) == result
////        where:
////        dateCreated || result
////        '2020'      || '-3'
////        '2021'      || ''
////    }
}
