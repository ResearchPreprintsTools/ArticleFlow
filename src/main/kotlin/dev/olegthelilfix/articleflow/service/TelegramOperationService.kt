package dev.olegthelilfix.articleflow.service

import dev.olegthelilfix.articleflow.data.TelegramOperationArguments
import dev.olegthelilfix.articleflow.service.operations.ErrorOperation
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update
import java.util.stream.Collectors

@Service
class TelegramOperationService(telegramOperation : List<BotOperation>,
                               private val errorOperation: ErrorOperation) {
    private val operations: Map<String, BotOperation> = telegramOperation.stream().collect(Collectors.toMap({it.getName()}, {it}))

    private val startMessage = listOf(telegramOperation.filter { it.isShowInDescription() }.joinToString(separator = "\n") {
        "*${it.getName()}* - ${it.getDescription()}"
    })

    fun executeOperation(update: Update): List<String> {
        val args = splitCommand(update.message.text)

        if(args[0] == "/start" || args[0] == "/help") {
            return startMessage
        }

        return operations.getOrDefault(args[0], errorOperation).execute(TelegramOperationArguments(args, update))
    }

    private fun splitCommand(message: String) = message.split(" ")
}
