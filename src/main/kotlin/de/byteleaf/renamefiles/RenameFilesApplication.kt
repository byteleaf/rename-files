package de.byteleaf.renamefiles

import de.byteleaf.renamefiles.control.RenameFileCommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RenameFilesApplication

fun main(args: Array<String>) {
    runApplication<RenameFileCommandLineRunner>(*args)
}