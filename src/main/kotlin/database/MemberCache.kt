package me.toddbensmiller.invisibledetector.database

import com.gitlab.kordlib.common.entity.Status

object MemberCache {
    val cache = HashMap<String, Status>()
    fun get(id: String): Status = cache[id] ?: Status.Offline // "unknown" status is the case of offline/invisible
}