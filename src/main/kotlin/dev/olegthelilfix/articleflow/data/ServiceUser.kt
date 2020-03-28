package dev.olegthelilfix.articleflow.data

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "ServiceUser")
class ServiceUser(): Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name="telegramId")
    var telegramId: Int = 0

    @Column(name="firstName")
    var firstName: String? = null

    @Column(name="lastName")
    var lastName: String? = null

    @Column(name="userName")
    var userName: String? = null

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
