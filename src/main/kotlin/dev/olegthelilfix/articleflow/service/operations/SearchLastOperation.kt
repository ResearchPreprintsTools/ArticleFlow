package dev.olegthelilfix.articleflow.service.operations

import dev.arxiv.name.data.Feed
import dev.arxiv.name.options.SearchField
import dev.arxiv.name.options.SortBy
import dev.arxiv.name.options.SortOrder
import dev.arxiv.name.requests.SearchRequest
import dev.arxiv.name.requests.SearchRequestExecutor
import dev.olegthelilfix.articleflow.data.TelegramOperationArguments
import dev.olegthelilfix.articleflow.service.BotOperation
import org.springframework.stereotype.Component

@Component
class SearchLastOperation : BotOperation {
    override fun execute(args: TelegramOperationArguments): List<String> {
        val request: SearchRequest = SearchRequest.SearchRequestBuilder
                .create(args.list[1], SearchField.ALL)
                .sortBy(SortBy.LAST_UPTATED_DATE)
                .sortOrder(SortOrder.DESCENDING)
                .maxResults(1)
                .build()

        val response: Feed = SearchRequestExecutor().executeAndMap(request)
        val entry = response.entry?.get(0)

        if (entry != null) {
            return listOf("published = ${entry.published}\nautors=${entry.author.joinToString(", ") { it.name }}\ntitle=${entry.title}\n\n${entry.summary}")
        }
        return listOf("Not found")
    }

    override fun getName() : String = "/search"
    override fun getDescription() : String = "Search last article by param"
}
