package dev.olegthelilfix.articleflow.conf

import org.springframework.context.annotation.Configuration


@Configuration
class TelegramBotSettings {
    val botUserName: String = System.getenv()["BOT"] ?: throw kotlin.RuntimeException("Bot name has not been found")

    val token: String = System.getenv()["TOKEN"] ?: throw kotlin.RuntimeException("Bot token has not been found")
}
