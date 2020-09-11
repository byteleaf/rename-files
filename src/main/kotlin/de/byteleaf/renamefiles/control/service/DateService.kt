package de.byteleaf.renamefiles.control.service

import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class DateService {

    fun formatDate(date: Date, fileNameFormat: String): String = SimpleDateFormat(fileNameFormat).format(date)

    fun parseDate(date: String, fileNameFormat: String): Date = SimpleDateFormat(fileNameFormat).parse(date)

    /**
     * To check weather a date string is a valid date or not!
     */
    fun isValidDate(date: String, fileNameFormat: String): Boolean = try {
        SimpleDateFormat(fileNameFormat).parse(date);
        true;
    } catch (ex: Exception) {
        false;
    }
}