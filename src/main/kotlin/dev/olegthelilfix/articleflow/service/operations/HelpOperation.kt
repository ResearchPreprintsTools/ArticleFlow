package dev.olegthelilfix.articleflow.service.operations

import org.springframework.stereotype.Component

@Component
class HelpOperation: StartOperation() {
    override fun getName() : String = "/help"
}
