package de.byteleaf.renamefiles.control.service

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.util.FileSystemUtils.deleteRecursively
import kotlin.test.assertEquals

/**
 * Integration test, try to rename real files!
 */
@RunWith(SpringRunner::class)
@ContextConfiguration(initializers = [ConfigFileApplicationContextInitializer::class], classes = [RenameFileService::class,
    PathLocationService::class, FileNameService::class, FileTypeService::class, CreationDateService::class, DateService::class, PrintService::class])
class RenameFileServiceTest {
    @Autowired
    private lateinit var service: RenameFileService

    @Autowired
    private lateinit var pathLocationService: PathLocationService
//    @MockBean
//    private lateinit var printService: PrintService

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
       // deleteTestFolder()
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
        // TODO
        assertEquals(5, folder.listFiles().size)
        //folder.resolve()
    }

    private fun fileExists(expectedFiles: List<String>) {

    }


}