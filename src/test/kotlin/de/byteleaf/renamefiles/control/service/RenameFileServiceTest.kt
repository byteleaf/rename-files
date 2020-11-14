package de.byteleaf.renamefiles.control.service

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.util.FileSystemUtils.deleteRecursively
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Integration test, try to rename real files!
 */
@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [RenameFileService::class, PathLocationService::class, FileNameService::class, FileTypeService::class,
    CreationDateService::class, DateService::class])
class RenameFileServiceTest {
    @Autowired
    private lateinit var service: RenameFileService

    @Autowired
    private lateinit var pathLocationService: PathLocationService

    @MockBean
    private lateinit var printService: PrintService

    private val targetFolder = "target"
    private val testFolderName = "test-rename-files"
    private val testFolder = "${targetFolder}/${testFolderName}"

    @Before
    fun setUp() {
        deleteTestFolder()
        pathLocationService.getFolder("test/rename-files").copyRecursively(pathLocationService.getFile(testFolder))
    }

    @After
    fun tearDown() {
        deleteTestFolder()
    }

    /**
     * Is deleting a temporary test folder if existing
     */
    private fun deleteTestFolder() {
        val folder = pathLocationService.getFile(testFolder)
        if (folder.exists()) {
            deleteRecursively(folder)
        }
    }

    /**
     * [RenameFileService.renameFile] will be tested within this test
     */
    @Test
    fun renameFolder() {
        service.run { renameFolder(testFolder, true, displayRenamed = true, fileNameFormat = "yyyy-MM-dd HH-mm-ss", fileNameSuffix = " suffix") }
        val folder = pathLocationService.getFolder(testFolder)
        validateFolder(folder, 6, listOf("2015-08-23 18-40-17 suffix.jpg", "2015-08-24 18-40-17 suffix.jpg", "2015-08-25 18-40-17 suffix.jpg",
                "2020-08-30 17-40-56 suffix.jpg", "image.unsupported"))
        validateFolder(folder.resolve("recursive"), 3, listOf("2015-08-23 18-40-17 suffix.jpg", "2015-08-23 18-40-17-1 suffix.jpg", "no-exif.jpg"))
    }

    private fun validateFolder(folder: File, expectedFileNumber: Int, expectedFileNames: List<String>) {
        assertEquals(expectedFileNumber, folder.listFiles().size)
        expectedFileNames.forEach {
            val file = folder.resolve(it)
            assertTrue(file.exists(), "File ${file.name} is not existing but should exist")
        }
    }


}