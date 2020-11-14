package de.byteleaf.renamefiles.control.runner

import com.beust.jcommander.JCommander
import com.maddenabbott.jcommander.controller.JCommanderController
import com.maddenabbott.jcommander.spring.JCommanderRunner
import de.byteleaf.renamefiles.control.error.ExceptionHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class AdvancedCommandRunner(val jCommanderController: JCommanderController, val exceptionHandler: ExceptionHandler, val jCommander: JCommander) : JCommanderRunner(jCommanderController) {

    override fun run(vararg args: String) {
        try {
            if (args.isEmpty()) {
                this.jCommander.usage()
            } else {
                this.jCommanderController.execute(args)
            }
        } catch (ex: Exception) {
            exceptionHandler.handleException(ex);
        }
    }
}