package dev.olegthelilfix.articleflow.service.operations

import dev.arxiv.name.data.Entry
import dev.olegthelilfix.articleflow.data.BotArguments

abstract class BotOperation {
    open fun execute(args: BotArguments) : List<String> = mutableListOf()
    open fun getName() : String = "ERROR"
}
