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

    fun renameFolder(relativeFolder: String, displayUnRenamed: Boolean, fileNameFormat: String, fileNameSuffix: String) {
        val parentFolder = pathLocationService.getFolder(relativeFolder)
        val statusOverview = HashMap<RenameStatus, MutableList<File>>()
        // TODO recursive for folders
        parentFolder.listFiles()?.forEach { child ->
            val status = renameFile(child, displayUnRenamed, fileNameFormat, fileNameSuffix)
            statusOverview.getOrPut(status) { mutableListOf() }.add(child)
        }
        // TODO print results

        // TODO print number of renamed
    }

    /**
     * Is renaming the file if necessary
     */
    fun renameFile(file: File, displayUnRenamed: Boolean, fileNameFormat: String, fileNameSuffix: String): RenameStatus {
        val path = Paths.get(file.absolutePath)
        val renameStatus = fileNameService.shouldRename(path, fileNameFormat, fileNameSuffix)
        if (renameStatus == RenameStatus.RENAMED) {
            Files.move(path, path.resolveSibling(fileNameService.generateName(path, fileNameFormat, fileNameSuffix)))
        }
        return renameStatus
    }
}