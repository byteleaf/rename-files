package de.byteleaf.renamefiles.service

import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class DateService {

    /**
     * Is converting a date to a string by a given format.
     * The UTC Time Zone will be used, because the dates will be parsed without timezone from Exif data and
     * we want to have the local time for the file name
     */
    fun formatDate(date: Date, fileNameFormat: String): String = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC"))
            .format(DateTimeFormatter.ofPattern(fileNameFormat))

    fun parseDate(date: String, fileNameFormat: String): Date = SimpleDateFormat(fileNameFormat).parse(date)

    /**
     * To check weather a date string is a valid date or not!
     */
    fun isValidDate(date: String, fileNameFormat: String): Boolean = try {
        parseDate(date, fileNameFormat)
        true
    } catch (ex: Exception) {
        false
    }
}