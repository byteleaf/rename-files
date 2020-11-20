package de.byteleaf.renamefiles.config

import com.beust.jcommander.JCommander
import com.maddenabbott.jcommander.controller.JCommanderController
import com.maddenabbott.jcommander.spring.JCommanderRunner
import de.byteleaf.renamefiles.config.JCommanderRunnerConfiguration
import de.byteleaf.renamefiles.error.ExceptionHandler
import de.byteleaf.renamefiles.service.PrintService
import de.byteleaf.renamefiles.helper.MockitoHelper
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [JCommanderRunnerConfiguration::class, ExceptionHandler::class])
class JCommanderRunnerConfigurationTest {

    @MockBean
    private lateinit var printService: PrintService
    @MockBean
    private lateinit var jCommander: JCommander
    @MockBean
    private lateinit var jCommanderController: JCommanderController
    @Autowired
    private lateinit var jCommandRunner: JCommanderRunner

    @Before
    fun setUp() {

    }

    @Test
    fun happyFlow() {
        jCommandRunner.run("-i")
    }

    @Test
    fun expectException() {
        `when`(jCommanderController.execute(MockitoHelper.anyObject())).thenThrow(RuntimeException("Test error"))
        jCommandRunner.run("-i")
        val inOrder = Mockito.inOrder(printService)
        inOrder.verify(printService).headline("An error occurred:")
        inOrder.verify(printService).error("Test error")
    }
}