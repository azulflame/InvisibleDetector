package me.toddbensmiller.invisibledetector.database

import org.jetbrains.exposed.sql.Database

object DBSettings {
    val db by lazy {
        Database.connect("jdbc:sqlite:Users.sqlite", "org.sqlite.JDBC")
    }
}