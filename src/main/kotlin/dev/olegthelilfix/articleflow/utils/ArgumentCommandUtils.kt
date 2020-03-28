package dev.olegthelilfix.articleflow.utils

import com.beust.jcommander.JCommander
import dev.arxiv.name.data.Entry
import dev.olegthelilfix.articleflow.data.BotArguments
import org.telegram.telegrambots.meta.api.objects.Update

@SuppressWarnings("SpreadOperator")
fun parseParams(update: Update): BotArguments {
    val botArguments = BotArguments(update)
    val argv: Array<String> = update.message.text.split(" ").toTypedArray()

    JCommander.newBuilder()
            .addObject(botArguments)
            .build()
            .parse(*argv)

    return botArguments
}

fun getInformationAboutCommand(): String {
    val sb = StringBuilder()

    val jCommander = JCommander.newBuilder().addObject(BotArguments(Update())).build()

    jCommander.programName = ""
    jCommander.usageFormatter.usage(sb)

    return sb.toString()
}

fun entryToMessage(entry: Entry): String {
    val title = entry.title.replace("\n", "")
    val authors = entry.author.joinToString(", ") { author -> author.name }
    val summary = entry.summary.replace("\n", "")
    val links = entry.link.joinToString("\n") { link ->
        val refTitle = link.title ?: "arxiv.org"

        "<a href=\"${link.href}\">${refTitle}</a>"
    }
    return "<b>${title}</b>\n<i>Authors:${authors}\n</i>\n${summary}\n\n${links}"
}
