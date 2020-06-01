package de.byteleaf.renamefiles.control.error

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ExceptionHandler {

    private val log: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

    fun handleException(ex: Exception) {
        when (ex) {
            is RuntimeException -> handleRuntimeException(ex)
            else -> handleFatalException(ex)
        }
    }

    /**
     * This handler will only be called if the exception is unexpected!
     * Should not happen in production.
     */
    fun handleFatalException(ex: Exception) {
        throw ex // Spring Boot default exception handling should handle this case!
    }

    fun handleRuntimeException(ex: RuntimeException) {
        log.warn(ex.message)
        if (ex.cause != null) {
            log.warn(ex.cause!!.message)
        }
    }
}