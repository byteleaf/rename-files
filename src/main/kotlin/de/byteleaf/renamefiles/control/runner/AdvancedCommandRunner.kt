package de.byteleaf.renamefiles.control.runner

import com.maddenabbott.jcommander.controller.JCommanderController
import com.maddenabbott.jcommander.spring.JCommanderRunner
import org.slf4j.Logger
import org.slf4j.LoggerFactory




class AdvancedCommandRunner(val jCommanderController: JCommanderController) : JCommanderRunner(jCommanderController) {

    var logger: Logger = LoggerFactory.getLogger(AdvancedCommandRunner::class.java)

    override fun run(vararg args: String) {
        try {
            this.jCommanderController.execute(args);
        }
        catch(ex: java.lang.Exception) {
            ex.printStackTrace();
            logger.warn(ex.message);
        }
    }
}