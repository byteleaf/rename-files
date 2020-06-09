package de.byteleaf.renamefiles.control.service

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

    fun renameFolder(relativeFolder: String, displayUnrenamed: Boolean, nameFormat: String) {
        val parentFolder = pathLocationService.getFolder(relativeFolder)
        var counterSuccess = 0
        var counterFail = 0
        // TODO recursive for folders
        parentFolder.listFiles()?.forEach { child ->
            if (renameFile(child, displayUnrenamed, nameFormat)) counterSuccess++ else counterFail++
        }
    }

    fun renameFile(file: File, displayUnrenamed: Boolean, nameFormat: String): Boolean {
        val path = Paths.get(file.absolutePath)
        if (fileTypeService.isFileTypeSupported(path)) {
            Files.move(path, path.resolveSibling(fileNameService.generateName(path, displayUnrenamed, nameFormat)))
            return true
        }
        return false
    }
}