package dev.olegthelilfix.articleflow.service.operations

import dev.olegthelilfix.articleflow.data.BotArguments
import dev.olegthelilfix.articleflow.service.BotOperation
import dev.olegthelilfix.articleflow.utils.getInformationAboutCommand
import org.springframework.stereotype.Component

@Component
class Start: BotOperation {
    private val startMessage = listOf("/search - find article by options", getInformationAboutCommand())

    override fun execute(args: BotArguments)= startMessage
    override fun getName() : String = "/start"
}
