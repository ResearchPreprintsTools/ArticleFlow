package dev.olegthelilfix.articleflow.data

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "FollowedArticle")
class FollowedArticle() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name="userId")
    var userId: Long = 0

    @Column(name="chatId")
    var chatId: Long = 0

    @Column(name = "searchRequest")
    lateinit var searchRequest: String

    @Column(name = "since")
    lateinit var since: Timestamp

    constructor(user: ServiceUser, searchRequest: String, chatId: Long): this() {
        this.searchRequest = searchRequest
        this.userId = user.id
        this.chatId = chatId

        this.since = Timestamp(System.currentTimeMillis())
    }

}
