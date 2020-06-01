package de.byteleaf.renamefiles.control.command

import com.beust.jcommander.Parameters
import com.maddenabbott.jcommander.controller.Command
import de.byteleaf.renamefiles.control.error.ExceptionHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.info.BuildProperties
import org.springframework.stereotype.Component


@Component
@Parameters(commandNames = arrayOf("-v"), commandDescription = "To display the current version of the application")
class VersionCommand : Command {

    private val log: Logger = LoggerFactory.getLogger(VersionCommand::class.java)

    @Autowired
    private lateinit var buildProperties: BuildProperties;

    override fun run() {
        log.info("App: {}", buildProperties.name)
        log.info("Version: {}", buildProperties.version)
        log.info("BuildTimeStamp: {}", buildProperties.time)
        log.info("ArtifactId: {}", buildProperties.artifact)
        log.info("GroupId: {}", buildProperties.group)
    }
}