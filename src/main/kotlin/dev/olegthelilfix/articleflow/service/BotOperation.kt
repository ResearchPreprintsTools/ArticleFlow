package dev.olegthelilfix.articleflow.service

import dev.olegthelilfix.articleflow.data.TelegramOperationArguments

interface BotOperation {
    fun execute(args: TelegramOperationArguments) : List<String> = mutableListOf()
    fun getName() : String = "ERROR"
    fun getDescription() : String = "ERROR"
    fun error() : String = "птчк вс очн плх."
    fun isShowInDescription(): Boolean = true
}
