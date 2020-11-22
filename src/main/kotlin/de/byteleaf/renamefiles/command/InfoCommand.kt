package de.byteleaf.renamefiles.command

import com.beust.jcommander.Parameters
import com.maddenabbott.jcommander.controller.Command
import de.byteleaf.renamefiles.constant.FileType
import de.byteleaf.renamefiles.service.PrintService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@Parameters(commandNames = arrayOf("-i", "--info"), commandDescription = "Additional information and examples how to use this tool")
class InfoCommand : Command {

    @Autowired
    private lateinit var print: PrintService

    override fun run() {
        print.headline("General information")
        print.title("Generated file names:")
        print.content("New generated file names are a combination of a timestamp format and a optional suffix.")
        print.content("The timestamp format is per default yyyy-MM-dd HH-mm-ss but can be changed via the -f parameter.")
        print.content("With the -s parameter the optional name suffix can be specified. If the suffix is ' Test' the filename could be '2020-05-12 23:30:10 Test.jpg'")
        print.content("The timestamp will be taken from the exif ExifSubIFDDirectory tag TAG_DATETIME_ORIGINAL. The local time from the place where it was made, will be used.")
        print.content("If multiple files have an identical timestamp a sequential number will be added: '2020-05-12_suffix.jpg', '2020-05-12-1_suffix.jpg', '2020-05-12-2_suffix.jpg'...")

        print.title("Supported file types:")
        print.content(FileType.values().joinToString(", "))

        print.headline("Examples")
        print.title("java -jar rename-files.jar")
        print.content("-i")
        print.content("-v")
        print.content("rf")
        print.content("rf")
        print.content("rf -d \"folder\" -f yyyy-MM-dd -s holiday -r")
        print.content("rf -h   (Hide all renamed files, only show unrenamed -> normally errors)")
    }
}