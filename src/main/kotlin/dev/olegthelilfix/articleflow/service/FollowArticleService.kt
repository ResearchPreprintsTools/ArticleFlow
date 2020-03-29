package dev.olegthelilfix.articleflow.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import dev.arxiv.name.requests.SearchRequest
import dev.olegthelilfix.articleflow.data.FollowedArticle
import dev.olegthelilfix.articleflow.data.ServiceUser
import dev.olegthelilfix.articleflow.repositories.FollowedArticleRepository
import org.springframework.stereotype.Service

@Service
class FollowArticleService(private val followedArticleRepository: FollowedArticleRepository) {
    private val objectMapper = ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    fun addFollowing(user: ServiceUser, searchRequestBuilder: SearchRequest.SearchRequestBuilder, chatId: Long): Boolean {
        synchronized(user) {
            val searchRequest = objectMapper.writeValueAsString(searchRequestBuilder)

            if (!followedArticleRepository.isRecordExist(user.id, searchRequest)) {
                followedArticleRepository.save(FollowedArticle(user, searchRequest, chatId))
                return true
            }

            return false
        }
    }

}
