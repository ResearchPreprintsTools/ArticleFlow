package dev.olegthelilfix.articleflow.service

import dev.olegthelilfix.articleflow.data.BotArguments

interface BotOperation {
    fun execute(args: BotArguments) : List<String> = mutableListOf()
    fun getName() : String = "ERROR"
}
