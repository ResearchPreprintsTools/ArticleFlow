package dev.olegthelilfix.articleflow.utils

import com.beust.jcommander.JCommander
import dev.olegthelilfix.articleflow.data.BotArguments

fun parseParams(args: String): BotArguments {
    val botArguments = BotArguments()
    val argv: Array<String> = args.split(" ").toTypedArray()

    JCommander.newBuilder()
            .addObject(botArguments)
            .build()
            .parse(*argv)

    return botArguments
}

fun getInformationAboutCommand(): String {
    val sb = StringBuilder()

    val jCommander = JCommander.newBuilder().addObject(BotArguments()).build()

    jCommander.programName = ""
    jCommander.usageFormatter.usage(sb)

    return sb.toString()
}
