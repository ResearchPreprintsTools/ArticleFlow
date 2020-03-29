package dev.olegthelilfix.articleflow.data

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "ServiceUser")
class ServiceUser(): Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name="telegramId")
    var telegramId: Int = 0

    @Column(name="firstName")
    lateinit var firstName: String

    @Column(name="lastName")
    lateinit var lastName: String

    @Column(name="userName")
    lateinit var userName: String

    constructor(user: TelegramUser) : this(){
        firstName = user.firstName
        lastName = user.lastName
        userName = user.userName
        telegramId = user.telegramId
    }

    override fun toString(): String {
        return "ServiceUser(id=$id, telegramId=$telegramId, firstName='$firstName', lastName='$lastName', userName='$userName')"
    }

}
