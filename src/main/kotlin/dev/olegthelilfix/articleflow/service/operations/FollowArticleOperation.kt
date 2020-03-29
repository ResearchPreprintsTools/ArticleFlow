package dev.olegthelilfix.articleflow.service.operations

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import dev.arxiv.name.options.SearchField
import dev.arxiv.name.options.SortBy
import dev.arxiv.name.options.SortOrder
import dev.arxiv.name.requests.SearchRequest
import dev.olegthelilfix.articleflow.data.BotArguments
import dev.olegthelilfix.articleflow.service.FollowArticleService
import dev.olegthelilfix.articleflow.service.UsersService
import org.springframework.stereotype.Component

@Component
class FollowArticleOperation(private val usersService: UsersService,
                             private val followArticle: FollowArticleService) : BotOperation() {
    override fun execute(args: BotArguments): List<String> {
        val request = SearchRequest.SearchRequestBuilder
                .create(args.valueToSearch, SearchField.ALL)
                .sortBy(SortBy.LAST_UPTATED_DATE)
                .sortOrder(SortOrder.DESCENDING)
                .maxResults(args.maxResult)

        args.and.forEach { request.and(it, SearchField.ALL) }
        args.or.forEach { request.and(it, SearchField.ALL) }
        args.andNot.forEach { request.and(it, SearchField.ALL) }

        if (followArticle.addFollowing(usersService.loadOrCreateUser(args.update), request, args.update.message.chatId)) {
            return listOf("DONE")
        }

        return listOf("FAILED")
    }

    override fun getName(): String = "/follow"
}
