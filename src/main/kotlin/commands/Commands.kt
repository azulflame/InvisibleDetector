package me.toddbensmiller.invisibledetector.commands

import me.jakejmattson.discordkt.api.arguments.IntegerArg
import me.jakejmattson.discordkt.api.dsl.commands
import me.toddbensmiller.invisibledetector.database.Users
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

fun respondingCommands() = commands("Response") {
    command("top")
    {
        description = "See who is invisible the most"
        execute(IntegerArg.makeOptional(5)) {
            var s = ""
            transaction(Connection.TRANSACTION_READ_UNCOMMITTED, 2) {
                s = Users.selectAll().orderBy(Users.messages).limit(args.first).toList().sortedBy { it[Users.messages] }
                    .joinToString("\n", "The ${args.first} most invisible users are:\n") {
                        "<@${it[Users.userID]}>: ${it[Users.messages]}"
                    }
            }
            respond(s)
        }
    }
}