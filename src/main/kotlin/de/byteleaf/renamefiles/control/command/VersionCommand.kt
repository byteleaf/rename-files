package de.byteleaf.renamefiles.control.command

import com.beust.jcommander.Parameters
import org.springframework.stereotype.Component

@Component
@Parameters(commandNames = arrayOf("-version"), commandDescriptionKey = "test_key")
class VersionCommand {

    fun run() {
        println("This is command one.")
    }
}