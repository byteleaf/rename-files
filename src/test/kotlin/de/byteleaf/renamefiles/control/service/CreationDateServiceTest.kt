package de.byteleaf.renamefiles.control.service

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import java.text.SimpleDateFormat
import kotlin.test.assertEquals

@RunWith(SpringRunner::class)
@ContextConfiguration(initializers = [ConfigFileApplicationContextInitializer::class], classes = [CreationDateService::class, PathLocationService::class, DateService::class])
class CreationDateServiceTest {
    @Autowired
    private lateinit var creationDateService: CreationDateService

    @Autowired
    private lateinit var pathLocationService: PathLocationService

    @Test
    fun getCreationDate() {
        val path = pathLocationService.getFile("test/test-sub/with-exif-tag-datetime.jpg").toPath()
        // TODO Only working in Timezone +2 !!
        assertEquals(SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2015-08-23 20:40:17"), creationDateService.getCreationDate(path))
    }

    @Test
    fun getCreationDateAsString() {
        val path = pathLocationService.getFile("test/test-sub/with-exif-tag-datetime.jpg").toPath()
        // TODO Only working in Timezone +2 !!
        assertEquals("2015-08-23", creationDateService.getCreationDateAsString(path, "YYYY-MM-dd"))
    }
}