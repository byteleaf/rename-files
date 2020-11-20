package de.byteleaf.renamefiles.service

import de.byteleaf.renamefiles.constant.FileType
import de.byteleaf.renamefiles.service.FileTypeService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import java.nio.file.Paths
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue


@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [FileTypeService::class])
class FileTypeServiceTest {
    @Autowired
    private lateinit var service: FileTypeService

    @Test
    fun getFileEnding() {
        assertEquals(".jpg", service.getFileEnding(Paths.get("./test.jpg")))
        assertEquals(".unknown", service.getFileEnding(Paths.get("test.unknown")))
    }

    @Test
    fun getFileType() {
        assertEquals(FileType.JPG, service.getFileType(Paths.get("./test.jpg")))
        assertNull(service.getFileType(Paths.get("/")))
        assertNull(service.getFileType(Paths.get("test.unknown")))
    }

    @Test
    fun isFileTypeSupported() {
        assertTrue { service.isFileTypeSupported(Paths.get("./test.jpg")) }
        assertFalse { service.isFileTypeSupported(Paths.get("test.unknown")) }
    }
}