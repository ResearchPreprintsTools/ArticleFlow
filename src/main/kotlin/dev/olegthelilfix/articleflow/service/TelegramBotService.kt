package dev.olegthelilfix.articleflow.service

import dev.olegthelilfix.articleflow.conf.TelegramBotSettings
import dev.olegthelilfix.articleflow.service.operations.ErrorOperation
import org.springframework.stereotype.Service
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update

@Service
class TelegramBotService(private val telegramBotSettings: TelegramBotSettings,
                         private val telegramOperationService: TelegramOperationService) : TelegramLongPollingBot() {
    private val botsApi = TelegramBotsApi()

    companion object {
        init {
            ApiContextInitializer.init()
        }
    }

    init {
        botsApi.registerBot(this)
    }

    override fun onUpdateReceived(update: Update) {
        try {
            telegramOperationService.executeOperation(update).forEach { update.sendMessage(it) }
        } catch (e: Exception) {
            update.sendMessage("птчк вс очн плх.\n$e")
        }
    }

    override fun getBotUsername() = telegramBotSettings.botUserName

    override fun getBotToken() = telegramBotSettings.token

    private fun Update.sendMessage(text: String): Message {
        val message: SendMessage = SendMessage(this.message.chatId, text).setParseMode("Markdown")

        return execute(message)
    }
}
