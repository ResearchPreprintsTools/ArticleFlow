package dev.olegthelilfix.articleflow.repositories

import dev.olegthelilfix.articleflow.data.ServiceUser
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface UserRepository: CrudRepository<ServiceUser, Long> {
    @Modifying
    @Transactional
    @Query("SELECT req FROM ServiceUser as req WHERE req.telegramId = :telegramId")
    fun findByTelegramId(telegramId: Int) : ServiceUser

    @Modifying
    @Transactional
    @Query("SELECT (count(id) > 0) as isExist FROM ServiceUser WHERE telegramId = :telegramId")
    fun isUserExist(telegramId: Int): Boolean
}
