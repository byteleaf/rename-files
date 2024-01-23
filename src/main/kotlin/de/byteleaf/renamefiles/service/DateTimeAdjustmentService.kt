package de.byteleaf.renamefiles.service

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
    private lateinit var fileTypeService: FileTypeService

    private val fileNameFormat = "yyyy-MM-dd HH-mm-ss";
    private val fileNameFormatRegex = Regex("""^\d{4}-\d{2}-\d{2} \d{2}-\d{2}-\d{2}""")

    fun adjustFolder(relativeFolder: String, recursive: Boolean, adjustment: DateTimeAdjustment) {
        val parentFolder = pathLocationService.getFolder(relativeFolder)
        val statusOverview = HashMap<Boolean, MutableList<File>>()
        adjustFolderInternal(parentFolder, statusOverview, recursive, adjustment)
        printService.printDateTimeAdjustmentReport(statusOverview)
    }

    private fun adjustFolderInternal(parentFolder: File, statusOverview: HashMap<Boolean, MutableList<File>>, recursive: Boolean, adjustment: DateTimeAdjustment) {
        parentFolder.listFiles()?.forEach { child ->
            if (child.isFile) {
                val result = adjustFile(child, adjustment)
                statusOverview.getOrPut(result.first) { mutableListOf() }.add(result.second)
            } else if (recursive) {
                adjustFolderInternal(child, statusOverview, recursive, adjustment)
            }
        }
    }

    private fun adjustFile(file: File, adjustment: DateTimeAdjustment): Pair<Boolean, File> {
        val originalPath = Paths.get(file.absolutePath)
        if (!isTimeAdjustmentPossible(originalPath)) {
            return Pair(false, file)
        }
        val suffixWithAppendix = getSuffixAndAppendix(originalPath)
        val newFileName = fileNameService.generateNameWithDateTimeAdjustment(originalPath, fileNameFormat, suffixWithAppendix, adjustment)
        val newPath = originalPath.resolveSibling(newFileName)
        Files.move(originalPath, newPath)
        return Pair(true, newPath.toFile())
    }

    private fun isTimeAdjustmentPossible(path: Path): Boolean {
        if (!fileTypeService.isFileTypeSupported(path)) return false
        val fileName = path.fileName.toFile().nameWithoutExtension
        val matchResult = fileNameFormatRegex.find(fileName)
        return matchResult != null
    }

    private fun getSuffixAndAppendix(path: Path): String {
        val fileName = path.fileName.toFile().nameWithoutExtension
        return fileNameFormatRegex.replace(fileName, "")
    }
}