package de.byteleaf.renamefiles.control.service

import de.byteleaf.renamefiles.constant.RenameStatus
import de.byteleaf.renamefiles.constant.FileType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

@Service
class RenameFileService {

    @Autowired
    private lateinit var fileNameService: FileNameService

    @Autowired
    private lateinit var pathLocationService: PathLocationService

    @Autowired
    private lateinit var printService: PrintService

    /**
     *
     */
    fun renameFolder(relativeFolder: String, displayUnRenamed: Boolean, displayRenamed: Boolean, fileNameFormat: String, fileNameSuffix: String) {
        val parentFolder = pathLocationService.getFolder(relativeFolder)
        val statusOverview = HashMap<RenameStatus, MutableList<File>>()
        renameFolderInternal(parentFolder, fileNameFormat, fileNameSuffix, statusOverview)
        printService.printStatusReport(statusOverview, displayUnRenamed, displayRenamed)
    }

    private fun renameFolderInternal(parentFolder: File, fileNameFormat: String, fileNameSuffix: String,
                                     statusOverview: HashMap<RenameStatus, MutableList<File>>) {
        parentFolder.listFiles()?.forEach { child ->
            if (child.isFile) {
                val result = renameFile(child, fileNameFormat, fileNameSuffix)
                statusOverview.getOrPut(result.first) { mutableListOf() }.add(result.second)
            } else {
                renameFolderInternal(child, fileNameFormat, fileNameSuffix, statusOverview)
            }
        }
    }

    /**
     * Is renaming the file if necessary
     * @return a pair of the [RenameStatus] and the the file, if [RenameStatus.RENAMED] the new file will be returned
     * else the original file
     */
    fun renameFile(file: File, fileNameFormat: String, fileNameSuffix: String): Pair<RenameStatus, File> {
        val originalPath = Paths.get(file.absolutePath)
        val renameStatus = fileNameService.shouldRename(originalPath, fileNameFormat, fileNameSuffix)
        if (renameStatus == RenameStatus.RENAMED) {
            val newPath = originalPath.resolveSibling(fileNameService.generateName(originalPath, fileNameFormat, fileNameSuffix))
            Files.move(originalPath, newPath)
            return Pair(renameStatus, newPath.toFile())
        }
        return Pair(renameStatus, originalPath.toFile())
    }
}