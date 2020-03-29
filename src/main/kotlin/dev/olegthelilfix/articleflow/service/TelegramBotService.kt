package dev.olegthelilfix.articleflow.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import dev.arxiv.name.options.SearchField
import dev.arxiv.name.options.SortBy
import dev.arxiv.name.options.SortOrder
import dev.arxiv.name.requests.SearchRequest
import dev.arxiv.name.utils.searchAllAfterDate
import dev.olegthelilfix.articleflow.conf.TelegramBotSettings
import dev.olegthelilfix.articleflow.repositories.FollowedArticleRepository
import dev.olegthelilfix.articleflow.repositories.UserRepository
import dev.olegthelilfix.articleflow.utils.entryToMessage
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Service
class TelegramBotService(private val telegramBotSettings: TelegramBotSettings,
                         private val telegramOperationService: TelegramOperationService,
                         private val usersService: UsersService,
                         private val followedArticleRepository: FollowedArticleRepository) : TelegramLongPollingBot() {
    private val log = LoggerFactory.getLogger(TelegramBotService::class.java)
    private val objectMapper = ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    private val botsApi = TelegramBotsApi()

    companion object {
        init {
            ApiContextInitializer.init()
        }
    }

    init {
        botsApi.registerBot(this)

        Executors.newSingleThreadScheduledExecutor()
                .scheduleWithFixedDelay({
                    try {
                        val scheduledArticle = followedArticleRepository.findAll()
                        scheduledArticle.map {
                            val request = objectMapper.readValue(it.searchRequest, Request::class.java)
                            searchAllAfterDate(request.toBuilder().build(), Date(it.since.time)).forEach { article ->
                                sendMessage(entryToMessage(article), it.chatId)
                            }
                            it.userId
                        }.toSet().forEach { followedArticleRepository.updateDate(it) }
                    } catch (e: java.lang.Exception) {
                        log.error("Follow is failed", e)
                    }
                }, 0, 1, TimeUnit.MINUTES)
    }

    override fun onUpdateReceived(update: Update) {
        try {
            val user = usersService.loadOrCreateUser(update)

            log.debug("Got request from $user")

            telegramOperationService.executeOperation(update).forEach { update.sendMessage(it) }
        } catch (e: Exception) {
            update.sendMessage("птчк вс очн плх.\n$e")
        }
    }

    override fun getBotUsername() = telegramBotSettings.botUserName

    override fun getBotToken() = telegramBotSettings.token

    private fun sendMessage(text: String, userId: Long): Message {
        val message: SendMessage = SendMessage(userId, text).setParseMode("html")

        return execute(message)
    }

    private fun Update.sendMessage(text: String): Message {
        return sendMessage(text, this.message.chatId)
    }

    private class Request {
        lateinit var arxivApiUri: String
        var sortBy: SortBy? = null
        var sortOrder: SortOrder? = null
        var start: Int? = null
        var maxResults: Int? = null
        var idList: String? = null
        lateinit var params: MutableList<SearchRequest.SearchOptions>
        lateinit var mainSearchOptions: String
        lateinit var mainSearchField: SearchField

        fun toBuilder(): SearchRequest.SearchRequestBuilder {
            return SearchRequest.SearchRequestBuilder.create(mainSearchOptions, mainSearchField)
                    .uri(arxivApiUri)
                    .sortBy(sortBy)
                    .sortOrder(sortOrder)
                    .start(start)
                    .maxResults(maxResults)
                    .idList(idList)
                    .params(params)
        }
    }
}
