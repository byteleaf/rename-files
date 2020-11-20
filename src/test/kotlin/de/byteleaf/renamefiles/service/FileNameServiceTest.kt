package de.byteleaf.renamefiles.service

import de.byteleaf.renamefiles.constant.RenameStatus
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
import kotlin.test.assertEquals


@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [FileNameService::class, DateService::class, FileTypeService::class, PathLocationService::class])
class FileNameServiceTest {
    @Autowired
    private lateinit var service: FileNameService

    @Autowired
    private lateinit var pathLocationService: PathLocationService

    @MockBean
    private lateinit var creationDateService: CreationDateService

    @Test
    fun shouldRename() {
        shouldRename("", Paths.get("f.notSupported"), RenameStatus.FILE_TYPE_NOT_SUPPORTED)
        shouldRename(null, Paths.get("f.jpg"), RenameStatus.CREATION_DATE_NOT_FOUND_IN_EXIF)
        shouldRename("", Paths.get("f.jpg"), RenameStatus.RENAMED)
        shouldRename("2015-08-23", Paths.get("2015-08-23-1_suffix.jpg"), RenameStatus.RENAME_NOT_NECESSARY)
    }

    private fun shouldRename(returnCreationDate: String?, input: Path, expectedResult: RenameStatus) {
        Mockito.`when`(creationDateService.getCreationDateAsString(MockitoHelper.anyObject(), Mockito.anyString(), MockitoHelper.anyObject())).thenReturn(returnCreationDate)
        assertEquals(expectedResult, service.shouldRename(input, "", "_suffix"))
    }

    @Test
    fun isRenameNecessary() {
        isRenameNecessary("2015-08-23 18-40-17", Paths.get("ExifSubIFDDirectory.TAG_TIMEZONE.jpg"), true) // name shorter than suffix
        isRenameNecessary("2015-08-23 18-40-17", Paths.get("2015-08-23 18-40-17_suffi1.jpg"), true) // not ends with correct suffix
        isRenameNecessary("2015-08-23 18-40-17", Paths.get("2015-08-23 18-40-33_suffix.jpg"), true) // not the correct date
        isRenameNecessary("2015-08-23 18-40-17", Paths.get("2015-08-23 18-40-17_suffix.jpg"), false)
        isRenameNecessary("2015-08-23 18-40-17", Paths.get("2015-08-23 18-40-17-1_suffix.jpg"), false)
        isRenameNecessary("2015-08-23 18-40-17", Paths.get("2015-08-23 18-40-17-91232_suffix.jpg"), false)
    }

    private fun isRenameNecessary(returnCreationDate: String?, input: Path, expectedResult: Boolean) {
        Mockito.`when`(creationDateService.getCreationDateAsString(MockitoHelper.anyObject(), Mockito.anyString(), MockitoHelper.anyObject())).thenReturn(returnCreationDate)
        assertEquals(expectedResult, service.isRenameNecessary(input, "yyyy-MM-dd HH-mm-ss", "_suffix"))
    }

    @Test
    fun getAppendix() {
        assertEquals("-3", service.getAppendix(pathLocationService.getFile("test/appendix/2020_suffix.jpg").toPath(),
                "2020", "_suffix", ".jpg"))     // File 2021_suffix.jpg is not existing
        assertEquals("", service.getAppendix(pathLocationService.getFile("test/appendix/2021_suffix.jpg").toPath(), "2021", "_suffix", ".jpg"))
        assertEquals("-1", service.getAppendix(pathLocationService.getFile("test/appendix/2019.jpg").toPath(), "2019", "", ".jpg"))
    }

    @Test
    fun generateName() {
        generateName("2019", pathLocationService.getFile("test/appendix/2019.jpg").toPath(), "", "2019-1.jpg")
        generateName("2020", pathLocationService.getFile("test/appendix/2020_suffix.jpg").toPath(), "_suffix", "2020-3_suffix.jpg")
        generateName("2021", pathLocationService.getFile("test/appendix/2021.jpg").toPath(), "_suffix", "2021_suffix.jpg")
    }

    private fun generateName(returnCreationDate: String?, input: Path, suffix: String, expectedResult: String) {
        Mockito.`when`(creationDateService.getCreationDateAsString(MockitoHelper.anyObject(), Mockito.anyString(), MockitoHelper.anyObject())).thenReturn(returnCreationDate)
        assertEquals(expectedResult, service.generateName(input, "YY", suffix))
    }

}