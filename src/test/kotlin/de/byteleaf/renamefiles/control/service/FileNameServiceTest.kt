package de.byteleaf.renamefiles.control.service

import de.byteleaf.renamefiles.helper.MockitoHelper
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*


@RunWith(SpringRunner::class)
/**
 * [DateService] & [FileTypeService] services have no dependencies so its easier not mocking them, in general you should mock all services!
 */
@ContextConfiguration(classes = [FileNameService::class, DateService::class, FileTypeService::class])
class FileNameServiceTest {
    @Autowired
    private lateinit var service: FileNameService
    @MockBean
    private lateinit var pathLocationService: PathLocationService
    @MockBean
    private lateinit var creationDateService: CreationDateService



    @Test
    fun isRenamePossible() {
        isRenamePossible(Date(), Paths.get("2015-08-23 18-40-17 CR_FOUND.notSupported"), false)
        isRenamePossible(Date(), Paths.get("2015-08 23 18-40-17 CR_FOUND.jpg"), true)
        isRenamePossible(null, Paths.get("2015-08 23 18-40-17 CR_NOT_FOUND.jpg"), false)
    }

    private fun isRenamePossible(returnCreationDateService: Date?, input: Path, expectedResult: Boolean) {
        Mockito.`when`(creationDateService.getCreationDate(MockitoHelper.anyObject())).thenReturn(null)
    }

//    @Test
//    fun generateName() {
//    }
//
//
//    @Test
//    fun getAppendix() {
//    }

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
}