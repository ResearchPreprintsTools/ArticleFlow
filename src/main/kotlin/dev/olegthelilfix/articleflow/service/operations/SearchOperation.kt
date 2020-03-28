package dev.olegthelilfix.articleflow.service.operations

import dev.arxiv.name.data.Feed
import dev.arxiv.name.options.SearchField
import dev.arxiv.name.options.SortBy
import dev.arxiv.name.options.SortOrder
import dev.arxiv.name.requests.SearchRequest
import dev.arxiv.name.requests.SearchRequestExecutor
import dev.olegthelilfix.articleflow.data.BotArguments
import dev.olegthelilfix.articleflow.service.BotOperation
import org.springframework.stereotype.Component

@Component
class SearchOperation : BotOperation {
    override fun execute(args: BotArguments): List<String> {
        val request = SearchRequest.SearchRequestBuilder
                .create(args.valueToSearch, SearchField.ALL)
                .sortBy(SortBy.LAST_UPTATED_DATE)
                .sortOrder(SortOrder.DESCENDING)
                .maxResults(args.maxResult)

        args.and.forEach { request.and(it, SearchField.ALL) }
        args.or.forEach { request.and(it, SearchField.ALL) }
        args.andNot.forEach { request.and(it, SearchField.ALL) }

        val response: Feed = SearchRequestExecutor().executeAndMap(request.build())

        return response.entry?.map {
            val title = it.title.replace("\n", "")
            val authors = it.author.joinToString(", ") { author -> author.name }
            val summary = it.summary.replace("\n", "")
            val links = it.link.joinToString("\n") { link ->
                val refTitle = link.title ?: "arxiv.org"

                "<a href=\"${link.href}\">${refTitle}</a>"
            }
            "<b>${title}</b>\n<i>Authors:${authors}\n</i>\n${summary}\n\n${links}"
        } ?: listOf("Not found")
    }

    override fun getName(): String = "/search"
}
