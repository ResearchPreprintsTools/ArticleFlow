package dev.olegthelilfix.articleflow.conf

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:settings.properties")
class TelegramBotSettings {
    @Value("\${telegram.botUserName}")
    lateinit var botUserName: String

    @Value("\${telegram.botToken}")
    lateinit var token: String
}
