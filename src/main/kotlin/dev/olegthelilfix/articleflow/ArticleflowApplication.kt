package dev.olegthelilfix.articleflow

import de.codecentric.boot.admin.server.config.EnableAdminServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableAdminServer
class ArticleflowApplication

fun main(args: Array<String>) {
    runApplication<ArticleflowApplication>(*args)
}
