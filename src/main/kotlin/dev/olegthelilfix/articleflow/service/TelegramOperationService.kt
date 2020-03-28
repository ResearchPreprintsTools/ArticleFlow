package dev.olegthelilfix.articleflow.service

import dev.olegthelilfix.articleflow.service.operations.ErrorOperation
import dev.olegthelilfix.articleflow.utils.parseParams
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update
import java.util.stream.Collectors

@Service
class TelegramOperationService(telegramOperation : List<BotOperation>, private val errorOperation: ErrorOperation) {
    private val operations: Map<String, BotOperation> = telegramOperation.stream().collect(Collectors.toMap({it.getName()}, {it}))

    fun executeOperation(update: Update): List<String> {
        val args = parseParams(update.message.text)

        return operations.getOrDefault(args.command, errorOperation).execute(args)
    }
}
