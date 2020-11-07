package de.byteleaf.renamefiles.constant

enum class RenameStatus {
    RENAMED, // If a file was renamed
    CREATION_DATE_NOT_FOUND_IN_EXIF,
    FILE_TYPE_NOT_SUPPORTED,
    RENAME_NOT_NECESSARY, // Filename is still in the correct pattern, rename not necessary!
}