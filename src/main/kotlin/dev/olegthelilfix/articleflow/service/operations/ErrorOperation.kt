package dev.olegthelilfix.articleflow.service.operations

import dev.olegthelilfix.articleflow.data.TelegramOperationArguments
import dev.olegthelilfix.articleflow.service.BotOperation
import org.springframework.stereotype.Component

@Component
class ErrorOperation: BotOperation {
    override fun execute(args: TelegramOperationArguments)= listOf("Something went wrong. Try again.")
    override fun isShowInDescription(): Boolean = false
}
