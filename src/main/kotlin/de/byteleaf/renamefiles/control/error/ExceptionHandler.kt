package de.byteleaf.renamefiles.control.error

import de.byteleaf.renamefiles.control.service.PrintService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ExceptionHandler {

    @Autowired
    private lateinit var printService: PrintService

    fun handleException(ex: Exception) {
        printService.headline("An error occurred:")
        printService.newLine()
        printService.error(ex.message!!)
        if (ex.cause != null) {
            printService.error(ex.cause!!.message!!)
        }
    }
}