plugins {
    kotlin("jvm") version "1.8.0"
    id("com.diffplug.spotless") version "6.16.0"
    application
}

group = "io.github.yip"

version = "1.0-SNAPSHOT"

repositories { mavenCentral() }

dependencies {
    implementation("io.github.realyusufismail:ydwk:1.3.0")
    testImplementation(kotlin("test"))
}

tasks.test { useJUnitPlatform() }

configurations { all { exclude(group = "org.slf4j", module = "slf4j-log4j12") } }

spotless {
    kotlin {
        // Excludes build folder since it contains generated java classes.
        targetExclude("build/**")
        ktfmt("0.42").dropboxStyle()

        licenseHeader(
            """/*
 * Copyright 2022 Yusuf's Islamic projects.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ """)
    }

    kotlinGradle {
        target("**/*.gradle.kts")
        ktfmt("0.42").dropboxStyle()
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }
}

kotlin { jvmToolchain(11) }

application { mainClass.set("TheIslamicDiscordBot") }
