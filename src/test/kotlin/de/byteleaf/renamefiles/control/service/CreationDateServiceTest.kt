package de.byteleaf.renamefiles.control.service

import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import java.text.SimpleDateFormat
import kotlin.test.assertEquals

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [CreationDateService::class, PathLocationService::class])
@TestPropertySource(properties = ["root-folder.name=rename-files"])
class CreationDateServiceTest {
    @Autowired
    private lateinit var creationDateService: CreationDateService
    @Autowired
    private lateinit var pathLocationService: PathLocationService

    @Test
    fun getCreationDate() {
        val path = pathLocationService.getFile("test/test-sub/with-exif-tag-datetime.jpg").toPath()
        assertEquals(SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2015-08-23 18:40:17"), creationDateService.getCreationDate(path))
    }
}