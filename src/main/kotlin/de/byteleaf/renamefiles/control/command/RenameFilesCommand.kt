package de.byteleaf.renamefiles.control.command

import com.beust.jcommander.Parameters
import com.maddenabbott.jcommander.controller.Command
import org.springframework.stereotype.Component

@Component
@Parameters(commandNames = arrayOf("-", "-v"), commandDescriptionKey = "test_key")
// TODO custom annotation required commands validation
class RenameFilesCommand : Command {

    override fun run() {
        println("This is command one.")
    }
}