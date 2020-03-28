package dev.olegthelilfix.articleflow.data

import org.telegram.telegrambots.meta.api.objects.Update

class TelegramUser(update: Update) {
    val telegramId: Int = update.message.from.id
    val firstName: String? = update.message.from.firstName
    val lastName: String? = update.message.from.lastName
    val userName: String? = update.message.from.userName

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TelegramUser

        if (telegramId != other.telegramId) return false

        return true
    }

    override fun hashCode(): Int {
        return telegramId
    }
}
