package de.byteleaf.renamefiles.service

import de.byteleaf.renamefiles.constant.RenameStatus
import de.byteleaf.renamefiles.model.TimeAdjustments
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
     * Is renaming all files in a folder. Same for all subfolder recursive.
     */
    fun renameFolder(relativeFolder: String, hideRenamed: Boolean, fileNameFormat: String, fileNameSuffix: String, recursive: Boolean, timeAdjustments: TimeAdjustments) {
        val parentFolder = pathLocationService.getFolder(relativeFolder)
        val statusOverview = HashMap<RenameStatus, MutableList<File>>()
        renameFolderInternal(parentFolder, fileNameFormat, fileNameSuffix, statusOverview, recursive, timeAdjustments)
        printService.printStatusReport(statusOverview, hideRenamed)
    }

    private fun renameFolderInternal(parentFolder: File, fileNameFormat: String, fileNameSuffix: String,
                                     statusOverview: HashMap<RenameStatus, MutableList<File>>, recursive: Boolean,  timeAdjustments: TimeAdjustments) {
        parentFolder.listFiles()?.forEach { child ->
            if (child.isFile) {
                val result = renameFile(child, fileNameFormat, fileNameSuffix, timeAdjustments)
                statusOverview.getOrPut(result.first) { mutableListOf() }.add(result.second)
            } else if(recursive) {
                renameFolderInternal(child, fileNameFormat, fileNameSuffix, statusOverview, recursive, timeAdjustments)
            }
        }
    }

    /**
     * Is renaming the file if necessary
     * @return a pair of the [RenameStatus] and the file, if [RenameStatus.RENAMED] the new file will be returned
     * else the original file
     */
    fun renameFile(file: File, fileNameFormat: String, fileNameSuffix: String, timeAdjustments: TimeAdjustments): Pair<RenameStatus, File> {
        val originalPath = Paths.get(file.absolutePath)
        val renameStatus = fileNameService.shouldRename(originalPath, fileNameFormat, fileNameSuffix, timeAdjustments)
        if (renameStatus == RenameStatus.RENAMED) {
            val newPath = originalPath.resolveSibling(fileNameService.generateName(originalPath, fileNameFormat, fileNameSuffix, timeAdjustments))
            Files.move(originalPath, newPath)
            return Pair(renameStatus, newPath.toFile())
        }
        return Pair(renameStatus, originalPath.toFile())
    }
}