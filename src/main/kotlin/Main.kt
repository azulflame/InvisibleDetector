package me.toddbensmiller.invisibledetector

import com.gitlab.kordlib.gateway.Intent
import com.gitlab.kordlib.gateway.PrivilegedIntent
import com.gitlab.kordlib.kordx.emoji.Emojis
import kotlinx.coroutines.flow.toList
import me.jakejmattson.discordkt.api.dsl.bot
import me.toddbensmiller.invisibledetector.database.DBSettings
import java.awt.Color
import java.lang.System.getenv


@PrivilegedIntent
suspend fun main(args: Array<String>) {
    // grab the token from the environment variables
    val token = getenv("BOT_ID")
    require(token != null)

    val x = DBSettings.db // unused variable, loads the db connection

    bot(token)
    {
        prefix { "!" }
        configure {
            // let commands be prefixed by a mention ("@Bot command")
            allowMentionPrefix = true
            generateCommandDocs = true
            recommendCommands = true
            commandReaction = Emojis.eyes
            theme = Color(0x00BFFF)
        }
        // everyone can run any command
        permissions { true }

        presence {
            watching("you")
        }
        intents {
            +Intent.GuildMessages
            +Intent.GuildMembers
            +Intent.GuildPresences
            +Intent.Guilds
        }
        onStart {
            val guildIds = api.guilds.toList().joinToString { it.name }
            println("Joined the guilds $guildIds")
        }
    }
}