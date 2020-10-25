package de.byteleaf.renamefiles.control.service

import org.junit.After
import org.junit.Before
import org.springframework.beans.factory.annotation.Autowired
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.util.FileSystemUtils.deleteRecursively
import java.io.File


@RunWith(SpringRunner::class)
@TestPropertySource(properties = ["root-folder.name=rename-files"])
@ContextConfiguration(classes = [RenameFileService::class, PathLocationService::class, PrintService::class])
class RenameFileServiceTest {
    @Autowired
    private lateinit var service: RenameFileService

    @Autowired
    private lateinit var pathLocationService: PathLocationService

    @MockBean
    private lateinit var fileNameService: FileNameService

    val testFolder = "target/test-rename-files"

    @Before
    fun setUp() {
        deleteTestFolder()
        pathLocationService.getFolder("test/rename-files").copyRecursively(pathLocationService.getFolder(testFolder))
    }

    /**
     * Is deleting a temporary test folder if existing
     */
    private fun deleteTestFolder() {
        val folder = pathLocationService.getFolder(testFolder)
        if (folder.exists()) {
            deleteRecursively(folder)
        }
    }

    @After
    fun tearDown() {
        deleteTestFolder()
    }

    @Test
    fun isRenameNecessary() {

    }

    private fun isRenameNecessary(fileName: String, fileNameSuffix: String?) {
       // pathLocationService.getFile()
    }

    @Test
    fun renameFolder() {
    }

    @Test
    fun renameFile() {
    }

}