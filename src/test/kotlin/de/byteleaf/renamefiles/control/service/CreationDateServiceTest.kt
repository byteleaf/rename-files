package de.byteleaf.renamefiles.control.service

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
    fun getCreationDateAsString() {
        assertEquals("2015-08-25 18-40-17",
                service.getCreationDateAsString(pathLocationService.getFile("test/creation-date/ExifSubIFDDirectory.TAG_DATETIME.jpg").toPath(),
                        "yyyy-MM-dd HH-mm-ss"))
        assertNull(service.getCreationDateAsString(pathLocationService.getFile("test/creation-date/no-exif.jpg").toPath(),
                        "yyyy-MM-dd HH-mm-ss"))
    }
}