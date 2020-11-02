package de.byteleaf.renamefiles.control.error.exception

import de.byteleaf.renamefiles.constant.RenameStatus
import java.lang.RuntimeException

class RenameStatusException : RuntimeException {
    constructor(renameStatus: RenameStatus): super()
}