package de.byteleaf.renamefiles.control.service

import com.drew.imaging.ImageMetadataReader
import com.drew.imaging.ImageProcessingException
import com.drew.metadata.Directory
import com.drew.metadata.exif.ExifIFD0Directory
import org.springframework.stereotype.Service
import java.io.IOException
import java.nio.file.Path
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

@Service
class FileNameService {

    @Throws(IOException::class, ImageProcessingException::class)
    fun generateNameByDate(path: Path): String {
        val dateCreated = getCreateTime(path)
        val fileEnding = getFileEnding(path)
        return dateCreated + getAppendix(dateCreated, fileEnding, path, 0) + getFileEnding(path)
    }

    fun getAppendix(dateCreated: String, fileEnding: String, path: Path, counter: Int): String {
        val counterString = if (counter > 0) "-$counter" else ""
        val sibling = path.resolveSibling(dateCreated + counterString + fileEnding)
        return if (sibling.toFile().exists()) {
            getAppendix(dateCreated, fileEnding, path, counter + 1)
        } else counterString
    }

    fun getFileEnding(path: Path): String {
        val fileName = path.toFile().name
        return fileName.substring(fileName.lastIndexOf("."))
    }

    @Throws(ImageProcessingException::class, IOException::class)
    fun getCreateTime(path: Path): String {
        val metadata = ImageMetadataReader.readMetadata(path.toFile())
        val directory: Directory? = metadata.getDirectory(ExifIFD0Directory::class.java)
        if (null != directory) {
            val tagDate = directory.getDate(ExifIFD0Directory.TAG_DATETIME)
            if (null != tagDate) {
                return formatDate(tagDate)
            }
        }
        // Try to parse date from file name
        val creationDate = parseDateFormFileName(path)
        if (creationDate != null) {
            return creationDate
        }
        throw RuntimeException(String.format("Could not retrieve file creation date, EXIF meta data is missing and date not found in name for file %s", path.toFile().name))
    }

    fun parseDateFormFileName(path: Path): String? {
        val name = path.fileName.toString()
        for (sign in Arrays.asList("-", "_")) {
            val pattern = Pattern.compile(String.format("[%s]\\d{8}[%s]", sign, sign))
            val matcher = pattern.matcher(name)
            if (matcher.find()) {
                val dateStr = name.substring(matcher.start() + 1, matcher.end() - 1)
                try {
                    val date = SimpleDateFormat("yyyyMMdd").parse(dateStr)
                    return formatDate(date)
                } catch (e: ParseException) { // Do nothing
                }
            }
        }
        return null
    }

    fun formatDate(date: Date?): String {
        val df = SimpleDateFormat("yyyy-MM-dd HH-mm-ss")
        return df.format(date)
    }
}