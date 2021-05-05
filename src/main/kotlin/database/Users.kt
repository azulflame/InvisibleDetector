package me.toddbensmiller.invisibledetector.database

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val userID: Column<String> = text("userID")
    val messages: Column<Int> = integer("invisibleMessages")
}