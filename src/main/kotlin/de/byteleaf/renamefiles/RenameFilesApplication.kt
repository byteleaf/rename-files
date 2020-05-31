package de.byteleaf.renamefiles

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RenameFilesApplication {
    fun main(args: Array<String>) {
        runApplication<RenameFilesApplication>(*args)
    }
}