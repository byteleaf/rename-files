package de.byteleaf.renamefiles.control.service

import org.springframework.stereotype.Service


@Service
class PrintService {

    fun headline(headline: String) {
        println("\u001b[46;1m ${headline} \u001b[0m")
    }

    fun title(title: String) {
        println("\u001b[1m ${title} \u001b[0m")
    }

    fun content(message: String) {
        println("  ${message}")
    }

    fun newLine() {
        println()
    }
}