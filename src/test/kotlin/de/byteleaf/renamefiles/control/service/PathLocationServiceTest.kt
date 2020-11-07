package de.byteleaf.renamefiles.control.service

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.info.BuildProperties
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(SpringRunner::class)
@ContextConfiguration(initializers = [ConfigFileApplicationContextInitializer::class], classes = [PathLocationService::class, BuildProperties::class])
class PathLocationServiceTest {

    @Autowired
    lateinit var pathLocationService: PathLocationService

    @Test
    fun getBaseFolder() {
        assertEquals("rename-files", pathLocationService.getBaseFolder().name)
    }

    @Test
    fun getFolder() {
        assertTrue(pathLocationService.getFolder("test").absolutePath.endsWith("rename-files\\test"))
        assertTrue(pathLocationService.getFolder("/test/path-location").absolutePath.endsWith("rename-files\\test\\path-location"))
        assertTrue(pathLocationService.getFolder("\\test/path-location").absolutePath.endsWith("rename-files\\test\\path-location"))
    }

    @Test(expected = RuntimeException::class)
    fun getFolderExpectFolderNotExisting() {
        pathLocationService.getFolder("unknownfolder")
    }

    @Test(expected = RuntimeException::class)
    fun getFolderExpectFolderIsFile() {
        pathLocationService.getFolder("test/path-location/test-file.md")
    }

    @Test
    fun getFile() {
        assertEquals("test-file.md", pathLocationService.getFile("test/path-location/test-file.md").name)
    }
}