package dev.olegthelilfix.articleflow.service

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import dev.olegthelilfix.articleflow.data.ServiceUser
import dev.olegthelilfix.articleflow.data.TelegramUser
import dev.olegthelilfix.articleflow.repositories.UserRepository
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update
import java.util.concurrent.TimeUnit

@Service
class UsersService (private val userRepository: UserRepository) {
    private val cache: LoadingCache<TelegramUser, ServiceUser> = CacheBuilder.newBuilder()
            .expireAfterWrite(60, TimeUnit.MINUTES)
            .build(object : CacheLoader<TelegramUser, ServiceUser>() {
                override fun load(key: TelegramUser): ServiceUser {
                    if (!userRepository.isUserExist(key.telegramId)) {
                        return userRepository.save(ServiceUser(key))
                    }

                    return userRepository.findByTelegramId(key.telegramId)
                }
            })

    fun loadOrCreateUser(update: Update): ServiceUser {
        return cache.get(TelegramUser((update)))
    }
}
