plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "io.github.yip"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.realyusufismail:ydwk:1.3.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("TheIslamicDiscordBot")
}