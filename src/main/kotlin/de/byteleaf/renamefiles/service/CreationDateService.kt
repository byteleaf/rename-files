package de.byteleaf.renamefiles.service

import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.Directory
import com.drew.metadata.Metadata
import com.drew.metadata.exif.ExifSubIFDDirectory
import com.drew.metadata.mp4.Mp4Directory
import de.byteleaf.renamefiles.constant.FileType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.nio.file.Path
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.*


@Service
class CreationDateService {

    data class MetaData(val directoryType: Class<out Directory>, val tag: Int)

    @Autowired
    private lateinit var dateService: DateService

    /**
     * Tries to receive (or parse) the creation date of the file MetaData ExifIF TAG_DATETIME
     */
    fun getCreationDateAsString(path: Path, fileNameFormat: String, fileType: FileType): String? {
        val metadata = ImageMetadataReader.readMetadata(path.toFile())
        return when (fileType) {
            FileType.JPG -> getCreationDateJPG(metadata, fileNameFormat)
            FileType.MP4 -> getCreationDateMP4(metadata, fileNameFormat)
        }
    }

    private fun getCreationDateJPG(metadata: Metadata, fileNameFormat: String): String? {
        val crDate = getExifData<Date>(metadata, listOf(MetaData(ExifSubIFDDirectory::class.java, ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL)))
        if (crDate != null) return dateService.formatDate(crDate, fileNameFormat)
        return null
    }

    private fun getCreationDateMP4(metadata: Metadata, fileNameFormat: String): String? {
        val crDate = getExifData<Date>(metadata, listOf(MetaData(Mp4Directory::class.java, Mp4Directory.TAG_CREATION_TIME)))
        if (crDate != null) {
            val cal = Calendar.getInstance()
            cal.time = crDate
            cal.add(Calendar.MINUTE, crDate.timezoneOffset * -1)
            return dateService.formatDate(cal.time, fileNameFormat)
        }
        return null
    }

    private fun <T> getExifData(metadata: Metadata, directories: List<MetaData>, returnDate: Boolean = true): T? {
        directories.forEach {
            val crDate = getExifData<T>(metadata, it.directoryType, it.tag, returnDate)
            if (crDate != null) {
                return crDate
            }
        }
        return null
    }

    private fun <T> getExifData(metadata: Metadata, directoryType: Class<out Directory>, tag: Int, returnDate: Boolean = false): T? {
        if (metadata.getDirectoriesOfType(directoryType).count() > 0) {
            if (returnDate) {
                return metadata.getFirstDirectoryOfType(directoryType).getDate(tag) as T
            }
            return metadata.getFirstDirectoryOfType(directoryType).getString(tag) as T
        }
        return null
    }
}