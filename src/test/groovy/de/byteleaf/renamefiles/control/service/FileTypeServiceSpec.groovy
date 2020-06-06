package de.byteleaf.renamefiles.control.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [FileTypeService.class])
class FileTypeServiceSpec {
        @Autowired
        FileTypeService fileTypeService

    def "when context is loaded then all expected beans are created"() {
        expect: "the SplitSlotsService is created"
        fileTypeService
    }
}