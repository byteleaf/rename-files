package de.byteleaf.renamefiles.service

import de.byteleaf.renamefiles.constant.FileType
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertEquals
import kotlin.test.assertNull

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [CreationDateService::class, PathLocationService::class, DateService::class])
class CreationDateServiceTest {
    @Autowired
    private lateinit var service: CreationDateService

    @Autowired
    private lateinit var pathLocationService: PathLocationService

    @Test
    fun getCreationDateAsStringJPG() {
        assertNull(getCreationDateAsString("jpg/no-exif.jpg", FileType.JPG))
        assertEquals("2015-08-25 18-40-17", getCreationDateAsString("jpg/ExifSubIFDDirectory.TAG_DATETIME.jpg", FileType.JPG))
        assertEquals("2020-11-15 21-08-41", getCreationDateAsString("jpg/crtime 21-08.jpg", FileType.JPG))
    }

    @Test
    fun getCreationDateAsStringMP4() {
        assertEquals("2020-11-15 21-18-46", getCreationDateAsString("mp4/time 21-18.mp4", FileType.MP4))
        assertEquals("2020-08-31 12-54-19", getCreationDateAsString("mp4/time 12-54.mp4", FileType.MP4))
    }

    private fun getCreationDateAsString(fileName: String, fileType: FileType): String? {
        return service.getCreationDateAsString(pathLocationService.getFile("test/creation-date/$fileName").toPath(),
                "yyyy-MM-dd HH-mm-ss", fileType)
    }
}