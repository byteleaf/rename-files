package de.byteleaf.renamefiles.control.command

import com.beust.jcommander.Parameters
import com.maddenabbott.jcommander.controller.Command
import org.springframework.stereotype.Component

@Component
@Parameters(commandNames = arrayOf("--version", "-v"), commandDescriptionKey = "To get the current version of the application")
class VersionCommand : Command {

    override fun run() {
        println("This is command one.")
    }
}