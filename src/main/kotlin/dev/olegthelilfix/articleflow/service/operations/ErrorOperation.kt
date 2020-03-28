package dev.olegthelilfix.articleflow.service.operations

import dev.olegthelilfix.articleflow.data.BotArguments
import dev.olegthelilfix.articleflow.service.BotOperation
import org.springframework.stereotype.Component

@Component
class ErrorOperation: BotOperation {
    override fun execute(args: BotArguments)= listOf("Something went wrong. Try again.")
}
