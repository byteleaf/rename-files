package de.byteleaf.renamefiles.control.service

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.info.BuildProperties
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [PathLocationService::class, BuildProperties::class])
@TestPropertySource(properties = ["root-folder.name=rename-files"])
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
        assertTrue(pathLocationService.getFolder("/test/test-sub").absolutePath.endsWith("rename-files\\test\\test-sub"))
        assertTrue(pathLocationService.getFolder("\\test/test-sub").absolutePath.endsWith("rename-files\\test\\test-sub"))
    }

    @Test(expected = RuntimeException::class)
    fun getFolderExpectFolderNotExisting() {
        pathLocationService.getFolder("unknownfolder")
    }

    @Test(expected = RuntimeException::class)
    fun getFolderExpectFolderIsFile() {
        pathLocationService.getFolder("test/README.md")
    }

    @Test
    fun getFile() {
        assertEquals("", pathLocationService.getFile("test/README.md").name)
    }
}