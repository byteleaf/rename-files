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


    fun renameFolder(folder: String, displayUnrenamed: Boolean) {
        val parentFolder = File(folder)
        if (parentFolder.exists()) {
            for (child in parentFolder.listFiles()) {
             //  renameFile(child, displayUnrenamed)
            }
        }
    }

    fun renameFile(file: File, displayUnrenamed: Boolean) {
        val path = Paths.get(file.absolutePath)
        if (fileTypeService.isFileTypeSupported(path)) {
            Files.move(path, path.resolveSibling(fileNameService.generateNameByDate(path)))
        }
    }
}