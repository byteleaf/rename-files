package de.byteleaf.renamefiles.control.service

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [PathLocationService::class])
class PathLocationServiceTestWithoutPropertiesTest {

    @Autowired
    private lateinit var pathLocationService: PathLocationService

    @Test(expected = IllegalStateException::class)
    fun getBaseFolder() {
        pathLocationService.getBaseFolder()
    }
}