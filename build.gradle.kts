import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
    application
}

group = "me.toddbensmiller"
version = "1.0"

repositories {
    jcenter()
}


dependencies {
    implementation("me.jakejmattson:DiscordKt:0.21.3")
    implementation("org.jetbrains.exposed:exposed:0.17.13")
    implementation("org.xerial:sqlite-jdbc:3.30.1")
}


tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("me.toddbensmiller.invisibledetector.MainKt")
}