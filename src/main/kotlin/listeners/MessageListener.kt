package me.toddbensmiller.invisibledetector.listeners

import com.gitlab.kordlib.common.entity.Status
import com.gitlab.kordlib.core.event.message.MessageCreateEvent
import me.jakejmattson.discordkt.api.dsl.listeners
import me.toddbensmiller.invisibledetector.database.MemberCache
import me.toddbensmiller.invisibledetector.database.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

fun messageListeners() = listeners {
    on<MessageCreateEvent> {
        if (message.author?.isBot == true) {
            return@on
        }
        message.author?.let { sender ->
            val id = sender.id.value
            val status = sender.asMemberOrNull(guildId!!)?.getPresenceOrNull()?.status ?: MemberCache.get(id)
            if (status == Status.Invisible || status == Status.Offline || status == Status.Unknown) {
                // increment the database for that user
                transaction(Connection.TRANSACTION_READ_UNCOMMITTED, 2) {
                    val result = Users.select { Users.userID eq id }.firstOrNull()
                    if (result == null) {
                        // create a new user with 1 cloaked message
                        Users.insert {
                            it[userID] = id
                            it[messages] = 1
                        }
                    } else {
                        // increment the counter
                        Users.update({ Users.userID eq id }) {
                            with(SqlExpressionBuilder) {
                                it.update(messages, messages + 1)
                            }
                        }
                    }
                }
            }
        }
    }
}