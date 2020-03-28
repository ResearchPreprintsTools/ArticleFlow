package dev.olegthelilfix.articleflow.repositories

import dev.olegthelilfix.articleflow.data.FollowedArticle
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.sql.Timestamp
import javax.transaction.Transactional

@Repository
interface FollowedArticleRepository : CrudRepository<FollowedArticle, Long> {
    @Transactional
    @Query("SELECT (count(id) > 0) as isExist FROM FollowedArticle WHERE userId = :userId AND searchRequest= :searchRequest")
    fun isRecordExist(userId: Long, searchRequest: String): Boolean

    @Modifying
    @Transactional
    @Query("UPDATE FollowedArticle SET since = :time WHERE userId = :userId")
    fun updateDate(userId: Long, time: Timestamp = Timestamp(System.currentTimeMillis()))
}
