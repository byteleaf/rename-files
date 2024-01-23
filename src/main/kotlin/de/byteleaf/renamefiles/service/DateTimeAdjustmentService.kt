package de.byteleaf.renamefiles.service

import de.byteleaf.renamefiles.constant.RenameStatus
import de.byteleaf.renamefiles.model.DateTimeAdjustment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Service
class DateTimeAdjustmentService {

    @Autowired
    private lateinit var fileNameService: FileNameService
    @Autowired
    private lateinit var printService: PrintService
    @Autowired
    private lateinit var pathLocationService: PathLocationService
    @Autowired
    private lateinit var creationDateService: CreationDateService
    @Autowired
    private lateinit var fileTypeService: FileTypeService

    fun adjust(adjustment: DateTimeAdjustment) {
        val renameStatus = fileNameService.shouldRename()
    }

    /**
     * Is adjusting the datetime for all files in a folder. Same for all subfolder recursive.
     */
    fun adjustFolder(relativeFolder: String, fileNameFormat: String, recursive: Boolean, adjustment: DateTimeAdjustment) {
        val parentFolder = pathLocationService.getFolder(relativeFolder)
        val statusOverview = HashMap<Boolean, MutableList<File>>()
        adjustFolderInternal(parentFolder, fileNameFormat, statusOverview, recursive, adjustment)
      //  printService.printStatusReport(statusOverview, hideRenamed)
    }

    private fun adjustFolderInternal(parentFolder: File, fileNameFormat: String, statusOverview: HashMap<Boolean, MutableList<File>>, recursive: Boolean, adjustment: DateTimeAdjustment) {
        parentFolder.listFiles()?.forEach { child ->
            if (child.isFile) {
                val result = adjustFile(child, fileNameFormat, adjustment)
                statusOverview.getOrPut(result.first) { mutableListOf() }.add(result.second)
            } else if(recursive) {
                adjustFolderInternal(child, fileNameFormat, statusOverview, recursive, adjustment)
            }
        }
    }

    /**
     * Is renaming the file if necessary
     * @return a pair of the [RenameStatus] and the file, if [RenameStatus.RENAMED] the new file will be returned
     * else the original file
     */
    private fun adjustFile(file: File, fileNameFormat: String, adjustment: DateTimeAdjustment): Pair<Boolean, File> {
        val originalPath = Paths.get(file.absolutePath)
        val renameStatus = fileNameService.shouldRename(originalPath, fileNameFormat, fileNameSuffix)
        if (renameStatus == RenameStatus.RENAMED) {
            val newPath = originalPath.resolveSibling(fileNameService.generateName(originalPath, fileNameFormat, fileNameSuffix))
            Files.move(originalPath, newPath)
            return Pair(renameStatus, newPath.toFile())
        }
        return Pair(renameStatus, originalPath.toFile())
    }

    private fun isTimeAdjustmentPossible(path: Path, fileNameFormat: String, adjustment: DateTimeAdjustment): Boolean {
        if (!fileTypeService.isFileTypeSupported(path)) return false
        if (creationDateService.getCreationDateAsString(path, fileNameFormat, fileTypeService.getFileType(path)!!) == null) return false
        return true
    }

    private fun getSuffix(path: Path, fileNameFormat: String, adjustment: DateTimeAdjustment): Boolean {
        if (!fileTypeService.isFileTypeSupported(path)) return false
        if (creationDateService.getCreationDateAsString(path, fileNameFormat, fileTypeService.getFileType(path)!!) == null) return false

        val fileName = path.fileName.toFile().nameWithoutExtension
        val creationDate = creationDateService.getCreationDateAsString(path, fileNameFormat, fileTypeService.getFileType(path)!!) ?: ""

        if (fileName.length < (fileNameSuffix + creationDate).length) return true
        if (!fileName.startsWith(creationDate) || !fileName.endsWith(fileNameSuffix)) return true
        val appendix = fileName.substring(creationDate.length, fileName.length - fileNameSuffix.length)
        if (appendix == "" || """-\d+""".toRegex().containsMatchIn(appendix)) {
            val newFileName = "$creationDate$appendix$fileNameSuffix"
            if (newFileName.equals(fileName)) return false
        }
        return true
    }
}