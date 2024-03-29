package de.byteleaf.renamefiles.command

import com.beust.jcommander.Parameter
import com.beust.jcommander.Parameters
import com.maddenabbott.jcommander.controller.Command
import de.byteleaf.renamefiles.model.DateTimeAdjustment
import de.byteleaf.renamefiles.service.DateTimeAdjustmentService
import de.byteleaf.renamefiles.service.RenameFileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@Parameters(commandNames = arrayOf("ta"), commandDescription = "To adjust the timestamp of a file. Usually if the photo was renamed with an invalid timezone.")
class DateTimeAdjustmentCommand : Command {

    @Parameter(names = arrayOf("--hoursadd", "-ha"), description = "To add hours to the timestamp")
    private var hoursAdd: Int = 0;

    @Parameter(names = arrayOf("--hourssubtract", "-hs"), description = "To add hours to the timestamp")
    private var hoursSubtract: Int = 0;


    @Parameter(names = arrayOf("--directory", "-d"), description = "Relative path to the top level directory (is working recursive)")
    private var directory: String = "./"

    @Parameter(names = arrayOf("--recursive", "-r"), description = "Rename files from subfolders recursive")
    private var recursive: Boolean = false

    @Autowired
    private lateinit var dateTimeAdjustmentService: DateTimeAdjustmentService

    override fun run() {
        dateTimeAdjustmentService.adjustFolder(directory, recursive, DateTimeAdjustment(hoursAdd, hoursSubtract))
    }
}