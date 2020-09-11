package de.byteleaf.renamefiles.control.service

import de.byteleaf.renamefiles.constant.FileType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

@Service
class RenameFileService {

    @Autowired
    private lateinit var fileTypeService: FileTypeService

    @Autowired
    private lateinit var fileNameService: FileNameService

    @Autowired
    private lateinit var pathLocationService: PathLocationService

    @Autowired
    private lateinit var print: PrintService

    fun renameFolder(relativeFolder: String, displayUnRenamed: Boolean, fileNameFormat: String, fileNameSuffix: String) {
        val parentFolder = pathLocationService.getFolder(relativeFolder)
        val unRenamedFiles = ArrayList<File>();
        // TODO recursive for folders
        parentFolder.listFiles()?.forEach { child ->
            if (!renameFile(child, displayUnRenamed, fileNameFormat, fileNameSuffix)) unRenamedFiles.add(child);
        }
    }

    /**
     * Is renaming a file if necessary!
     * @return false if its not possible to rename the file (could happen for example if the [FileType] is not supported)
     */
    fun renameFile(file: File, displayUnRenamed: Boolean, fileNameFormat: String, fileNameSuffix: String): Boolean {
        val path = Paths.get(file.absolutePath)
        if (fileTypeService.isFileTypeSupported(path)) {
            if (fileNameService.isRenameNecessary(path, fileNameFormat, fileNameSuffix)) {
                Files.move(path, path.resolveSibling(fileNameService.generateName(path, fileNameFormat, fileNameSuffix)))
            }
            return true
        }
        return false
    }
}