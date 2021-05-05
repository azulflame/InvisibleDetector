package me.toddbensmiller.invisibledetector.listeners

import com.gitlab.kordlib.common.entity.Status
import com.gitlab.kordlib.core.event.guild.GuildCreateEvent
import kotlinx.coroutines.flow.*
import me.jakejmattson.discordkt.api.dsl.listeners
import me.toddbensmiller.invisibledetector.database.MemberCache

fun guildListeners() = listeners {
    on<GuildCreateEvent>
    {
        guild.members.onStart { println("Pulling members for guild ${guild.name}") }
            .onEach { MemberCache.cache[it.id.value] = it.getPresenceOrNull()?.status ?: Status.Unknown }
            .onCompletion { println("Finished pulling members for guild ${guild.name}") }
            .collect()
    }
}