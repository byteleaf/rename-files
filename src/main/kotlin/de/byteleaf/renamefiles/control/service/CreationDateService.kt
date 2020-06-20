package de.byteleaf.renamefiles.control.service

import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.Directory
import com.drew.metadata.exif.ExifIFD0Directory
import org.springframework.stereotype.Service
import java.nio.file.Path
import java.util.*

@Service
class CreationDateService {

    /**
     * Tries to receive (or parse) the creation date of a file from this sources:
     * in this order:
     * 1) MetaData ExifIF TAG_DATETIME
     * 2) Try to parse date from filename
     */
    fun getCreationDate(path: Path): Date? {
        var crDate: Date? = null
        val metadata = ImageMetadataReader.readMetadata(path.toFile())
        val directory: Directory? = metadata.getDirectory(ExifIFD0Directory::class.java)
        if (null != directory) {
            crDate = directory.getDate(ExifIFD0Directory.TAG_DATETIME)
        }


        return crDate

        // TODO parse from file name
        // Try to parse date from file name
//        val creationDate = parseDateFormFileName(path)
//        if (creationDate != null) {
//            return creationDate
//        }
     //   throw RuntimeException("Could not retrieve file creation date, EXIF meta data is missing and date not found in name for file ${path.toFile().name}")
    }

//    fun parseDateFormFileName(path: Path): String? {
//        val name = path.fileName.toString()
//        for (sign in Arrays.asList("-", "_")) {
//            val pattern = Pattern.compile(String.format("[%s]\\d{8}[%s]", sign, sign))
//            val matcher = pattern.matcher(name)
//            if (matcher.find()) {
//                val dateStr = name.substring(matcher.start() + 1, matcher.end() - 1)
//                try {
//                    val date = SimpleDateFormat("yyyyMMdd").parse(dateStr)
//                    return formatDate(date)
//                } catch (e: ParseException) { // Do nothing
//                }
//            }
//        }
//        return null
//    }
}