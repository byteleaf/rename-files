package de.byteleaf.renamefiles.control.service

import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.exif.ExifIFD0Directory
import de.byteleaf.renamefiles.constant.RenameStatus
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import java.nio.file.Path
import java.text.SimpleDateFormat
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNull
import com.drew.metadata.Metadata
import kotlin.test.assertFailsWith

@RunWith(SpringRunner::class)
@ContextConfiguration(initializers = [ConfigFileApplicationContextInitializer::class], classes = [CreationDateService::class, PathLocationService::class, DateService::class])
class CreationDateServiceTest {
    @Autowired
    private lateinit var service: CreationDateService

    @Autowired
    private lateinit var pathLocationService: PathLocationService

    private lateinit var path: Path
    private lateinit var metadata: Metadata


    private fun getPath() = pathLocationService.getFile("test/test-sub/ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL.jpg").toPath()

    private fun getMetadata(fileName: String) = ImageMetadataReader.readMetadata(path.toFile())

    @Test
    fun getCreationDateFromExif() {
        val date = service.getCreationDateFromExif(metadata)

//        val exception = assertFailsWith<MyException> {method()}
//        assertThat(exception.message, equalTo("oops!"))
        // TODO Only working in Timezone +2 !!
        // assertEquals(SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2015-08-23 18:40:17"), creationDateService.getCreationDate(path))
    }

    @Ignore
    @Test
    fun getCreationDate() {
        val path = pathLocationService.getFile("test/test-sub/ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL.jpg").toPath()
        val i = 9
        // TODO Only working in Timezone +2 !!
       // assertEquals(SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2015-08-23 18:40:17"), creationDateService.getCreationDate(path))
    }

    @Ignore
    @Test
    fun getCreationDateNoExif() {
        val path = pathLocationService.getFile("test/test-sub/no-exif.jpg").toPath()
        // TODO Only working in Timezone +2 !!
      //  assertNull(creationDateService.getCreationDate(path))
    }

    @Ignore
    @Test
    fun getCreationDateAsString() {
//        val path = pathLocationService.getFile("test/test-sub/ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL.jpg").toPath()
//        // TODO Only working in Timezone +2 !!
//        assertEquals("2015-08-23", creationDateService.getCreationDateAsString(path, "YYYY-MM-dd"))
    }
}