package de.byteleaf.renamefiles.service

import de.byteleaf.renamefiles.model.DateTimeAdjustment
import de.byteleaf.renamefiles.service.*
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
@ContextConfiguration(classes = [DateTimeAdjustmentService::class, PathLocationService::class, FileNameService::class, FileTypeService::class, CreationDateService::class, DateService::class])
class DateTimeAdjustmentServiceTest {
    @Autowired
    private lateinit var service: DateTimeAdjustmentService
    @Autowired
    private lateinit var pathLocationService: PathLocationService
    @MockBean
    private lateinit var printService: PrintService

    private val targetFolder = "target"
    private val testFolderName = "test-date-time-adjustment"
    private val testFolder = "${targetFolder}/${testFolderName}"

    @Before
    fun setUp() {
        deleteTestFolder()
        pathLocationService.getFolder("test/date-time-adjustment").copyRecursively(pathLocationService.getFile(testFolder))
    }

    @After
    fun tearDown() {
        //deleteTestFolder()
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

    @Test
    fun adjustFolder() {
        service.run { adjustFolder(testFolder, recursive = true, adjustment = DateTimeAdjustment(2)) }
        val folder = pathLocationService.getFolder(testFolder)
        validateFolder(folder, 3, listOf("2015-08-23 20-40-17_suffix.jpg", "2015-08-23 20-40-17_already two hours added.jpg"))
        validateFolder(folder.resolve("recursive"), 3, listOf("starts_invalid 2015-08-23 18-40-00.jpg", "2015-08-23 20-40-17-03_with appendix.jpg", "2015-08-23 20-40-17_valid file in recursive folder.jpg"))
    }

    private fun validateFolder(folder: File, expectedFileNumber: Int, expectedFileNames: List<String>) {
        assertEquals(expectedFileNumber, folder.listFiles().size)
        expectedFileNames.forEach {
            val file = folder.resolve(it)
            assertTrue(file.exists(), "File ${file.name} is not existing but should exist")
        }
    }


}