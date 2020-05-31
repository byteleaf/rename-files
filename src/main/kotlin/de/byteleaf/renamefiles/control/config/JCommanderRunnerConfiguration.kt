package de.byteleaf.renamefiles.control.config

import com.maddenabbott.jcommander.controller.JCommanderController
import com.maddenabbott.jcommander.spring.JCommanderRunner
import de.byteleaf.renamefiles.control.error.ExceptionHandler
import de.byteleaf.renamefiles.control.runner.AdvancedCommandRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JCommanderRunnerConfiguration {

    @Bean
    fun jCommanderRunner(jCommanderController: JCommanderController, exceptionHandler: ExceptionHandler): JCommanderRunner? {
        return AdvancedCommandRunner(jCommanderController, exceptionHandler)
    }
}