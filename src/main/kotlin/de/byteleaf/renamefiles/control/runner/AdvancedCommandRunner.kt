package de.byteleaf.renamefiles.control.runner

import com.maddenabbott.jcommander.controller.JCommanderController
import com.maddenabbott.jcommander.spring.JCommanderRunner
import de.byteleaf.renamefiles.control.error.ExceptionHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class AdvancedCommandRunner(val jCommanderController: JCommanderController, val exceptionHandler: ExceptionHandler) : JCommanderRunner(jCommanderController) {

    var logger: Logger = LoggerFactory.getLogger(AdvancedCommandRunner::class.java)


    override fun run(vararg args: String) {
        try {
            this.jCommanderController.execute(args);
        } catch (ex: Exception) {
            exceptionHandler.handleException(ex);
        }
    }
}