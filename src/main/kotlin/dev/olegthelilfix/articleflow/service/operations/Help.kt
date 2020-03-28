package dev.olegthelilfix.articleflow.service.operations

import org.springframework.stereotype.Component

@Component
class Help: Start() {
    override fun getName() : String = "/help"
}
