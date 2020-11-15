package de.byteleaf.renamefiles.control.config

import com.beust.jcommander.JCommander
import com.maddenabbott.jcommander.controller.JCommanderController
import com.maddenabbott.jcommander.spring.JCommanderRunner
import de.byteleaf.renamefiles.control.error.ExceptionHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JCommanderRunnerConfiguration {

    @Autowired
    private lateinit var jCommander: JCommander

    @Bean
    fun jCommanderRunner(jCommanderController: JCommanderController, exceptionHandler: ExceptionHandler): JCommanderRunner? {
        return AdvancedCommandRunner(jCommanderController, exceptionHandler, jCommander)
    }
}