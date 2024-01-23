package de.byteleaf.renamefiles.service

import de.byteleaf.renamefiles.constant.RenameStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File


@Service
class PrintService {

    private val ANSI_RESET = "\u001B[0m"
    private val ANSI_BLUE_BG_BOLD = "\u001B[46;1m"
    private val ANSI_BOLD = "\u001B[1m"
    private val ANSI_RED = "\u001B[31m"

    private val MAX_SIGNS_PER_LINE = 77;


    @Autowired
    private lateinit var pathLocationService: PathLocationService

    fun headline(headline: String) {
        newLine()
        println("$ANSI_BLUE_BG_BOLD${headline}$ANSI_RESET")
    }

    fun title(title: String) {
        newLine()
        println("$ANSI_BOLD ${title}$ANSI_RESET")
    }

    fun content(message: String) {
        message.chunked(MAX_SIGNS_PER_LINE).forEach {
            println("  ${it}")
        }
    }

    fun newLine() {
        println()
    }

    fun error(message: String) {
        println("$ANSI_RED ${message} \u001b[0m")
    }

    fun printStatusReport(statusOverview: HashMap<RenameStatus, MutableList<File>>, hideRenamed: Boolean) {
        headline("Report")
        statusOverview.keys.forEach { key ->
            when(key) {
                RenameStatus.FILE_TYPE_NOT_SUPPORTED -> printReport("Unsupported file types", statusOverview[key], true)
                RenameStatus.CREATION_DATE_NOT_FOUND_IN_EXIF -> printReport("Creation date not found", statusOverview[key], true)
                RenameStatus.RENAME_NOT_NECESSARY -> printReport("Rename not necessary", statusOverview[key])
                RenameStatus.RENAMED -> if (!hideRenamed) printReport("Renamed", statusOverview[key])
                else -> throw RuntimeException("The reporting for RenameStatus $key is not implemented!")
            }
        }
    }

    fun printDateTimeAdjustmentReport(statusOverview: HashMap<Boolean, MutableList<File>>) {
        headline("Report")
        statusOverview.keys.forEach { key ->
            if(key) {
                printReport("DateTime Adjusted", statusOverview[key])
            } else {
                printReport("DateTime not adjusted", statusOverview[key], true)
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