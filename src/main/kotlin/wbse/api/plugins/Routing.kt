package wbse.api.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Here will be service external api (for tg bot/site/whatever)")
        }
    }
}
