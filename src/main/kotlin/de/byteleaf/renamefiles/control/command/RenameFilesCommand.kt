package de.byteleaf.renamefiles.control.command

import com.beust.jcommander.Parameter
import com.beust.jcommander.Parameters
import com.maddenabbott.jcommander.controller.Command
import de.byteleaf.renamefiles.control.service.RenameFileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@Parameters(commandNames = arrayOf("rf"), commandDescription = "To rename files by a defined schema")
class RenameFilesCommand : Command {

    @Parameter(names = arrayOf("--directory", "-d"), description = "Relative path to the top level directory (is working recursive)", required = true)
    private lateinit var directory: String

    @Parameter(names = arrayOf("--unrenamed", "-u"), description = "Display all files, which could not be renamed, could happen if the file type is not supported")
    private var displayUnRenamed: Boolean = true

    @Parameter(names = arrayOf("--renamed", "-r"), description = "Display all renamed files")
    private var displayRenamed: Boolean = false

    @Parameter(names = arrayOf("--format", "-f"), description = "The format pattern for the new file name")
    private var fileNameFormat: String = "yyyy-MM-dd HH-mm-ss"

    @Parameter(names = arrayOf("--suffix", "-s"), description = "A static file name suffix")
    private var fileNameSuffix: String = ""

    @Autowired
    private lateinit var renameFileService: RenameFileService

    override fun run() {
        renameFileService.renameFolder(directory, displayUnRenamed, displayRenamed, fileNameFormat, fileNameSuffix)
    }
}