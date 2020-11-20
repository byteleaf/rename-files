package de.byteleaf.renamefiles.command

import com.beust.jcommander.Parameters
import com.maddenabbott.jcommander.controller.Command
import de.byteleaf.renamefiles.service.PrintService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.info.BuildProperties
import org.springframework.stereotype.Component


@Component
@Parameters(commandNames = arrayOf("-v", "--version"), commandDescription = "To display the current version of the application")
class VersionCommand : Command {

    @Autowired
    private lateinit var print: PrintService
    @Autowired
    private lateinit var buildProperties: BuildProperties;

    override fun run() {
        print.headline("Version")
        print.content("App: ${buildProperties.name}")
        print.content("Version: ${buildProperties.version}")
        print.content("BuildTimeStamp: ${buildProperties.time}")
        print.content("ArtifactId: ${buildProperties.artifact}")
        print.content("GroupId: ${buildProperties.group}")
    }
}