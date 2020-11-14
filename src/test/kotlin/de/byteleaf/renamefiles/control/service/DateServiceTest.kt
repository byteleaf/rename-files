package de.byteleaf.renamefiles.control.service

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [DateService::class])
class DateServiceTest {

    @Autowired
    private lateinit var service: DateService

    @Test
    fun formatDate() {
        val date = SimpleDateFormat("yyyy-MM-dd HH-mm-ss Z", Locale.ENGLISH).parse("2020-10-01 01-02-03 +0000")
        assertEquals("2020-10-01 01-02-03", service.formatDate(date, "yyyy-MM-dd HH-mm-ss"))
    }

    @Test
    fun parseDate() {
        assertEquals(Date(120, 9, 1), service.parseDate("2020-10-01", "yyyy-MM-dd"))
    }

    @Test
    fun isValidDate() {
        assertTrue { service.isValidDate("2020-10-01 23-01-13", "yyyy-MM-dd HH-mm-ss") }
        assertFalse { service.isValidDate("blablbala", "yyyy-MM-dd HH-mm-ss") }
        assertTrue { service.isValidDate("2020-3-3 1-1-1", "yyyy-MM-dd HH-mm-ss") }
        assertFalse { service.isValidDate("2020-3-3 1-1", "yyyy-MM-dd HH-mm-ss") }
    }
}