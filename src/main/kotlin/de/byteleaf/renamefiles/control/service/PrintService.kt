package de.byteleaf.renamefiles.control.service

import de.byteleaf.renamefiles.constant.RenameStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File
import java.lang.RuntimeException


@Service
class PrintService {

    @Autowired
    private lateinit var pathLocationService: PathLocationService

    fun headline(headline: String) {
        println("\u001b[46;1m ${headline} \u001b[0m")
    }

    fun title(title: String) {
        println("\u001b[1m ${title} \u001b[0m")
    }

    fun content(message: String) {
        println("  ${message}")
    }

    fun newLine() {
        println()
    }

    fun error(message: String) {
        println("\u001B[31m[1m ${message} \u001b[0m")
    }

    fun printStatusReport(statusOverview: HashMap<RenameStatus, MutableList<File>>, displayUnRenamed: Boolean, displayRenamed: Boolean) {
        headline("Report")
        statusOverview.keys.forEach { key ->
            when(key) {
                RenameStatus.FILE_TYPE_NOT_SUPPORTED -> if(displayUnRenamed) printReport("Unsupported file types", statusOverview[key], true)
                RenameStatus.CREATION_DATE_NOT_FOUND_IN_META_DATA -> if(displayUnRenamed) printReport("Creation date not found", statusOverview[key], true)
                RenameStatus.RENAME_NOT_NECESSARY -> if(displayUnRenamed) printReport("Unsupported file types", statusOverview[key])
                RenameStatus.RENAMED -> if(displayRenamed) printReport("Renamed", statusOverview[key])
                else -> throw RuntimeException("The reporting for RenameStatus $key is not implemented!")
            }
        }
    }

    private fun printReport(title: String, files: MutableList<File>?, isError: Boolean = false) {
        if(files != null) {
            title("$title (${files.size})")
            files.forEach { file ->
                if(isError) error(pathLocationService.getRelativePath(file))
                else content(pathLocationService.getRelativePath(file))
            }
        }
    }
}