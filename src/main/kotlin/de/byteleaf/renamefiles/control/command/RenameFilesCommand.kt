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

    @Parameter(names = arrayOf("--folder", "-f"), description = "Relative path to the top level folder (is working recursive)", required = true)
    private lateinit var folder: String

    // TODO Name Format

    @Parameter(names = arrayOf("--unrenamed", "-u"), description = "Display all files, which could not be renamed")
    private var displayUnrenamed: Boolean = false

    @Autowired
    private lateinit var renameFileService: RenameFileService

    override fun run() {
        renameFileService.renameFolder(folder, displayUnrenamed)
    }
}