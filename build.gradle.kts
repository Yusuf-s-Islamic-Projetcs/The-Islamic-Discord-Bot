import io.github.realyusufismail.jconfig.*
import nu.studer.gradle.jooq.JooqEdition
import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.Logging

buildscript {
    repositories { mavenCentral() }
    dependencies {
        classpath("org.postgresql:postgresql:42.5.4")
        classpath("io.github.realyusufismail:jconfig:1.0.8")
    }
}

plugins {
    kotlin("jvm") version "1.8.20"
    id("com.diffplug.spotless") version "6.16.0"
    id("nu.studer.jooq") version "8.1"
    application
    jacoco // code coverage reports
}

group = "io.github.yip"

version = "1.0-SNAPSHOT"

repositories { mavenCentral() }

dependencies {
    // YDWK
    implementation("io.github.realyusufismail:ydwk:1.6.0")
    // JOOQ
    implementation("org.jooq:jooq:3.18.0")
    implementation("org.jooq:jooq-meta:3.18.0")
    implementation("org.jooq:jooq-codegen:3.18.0")
    // logger
    implementation("ch.qos.logback:logback-classic:1.4.5")
    implementation("ch.qos.logback:logback-core:1.4.5")
    implementation("uk.org.lidalia:sysout-over-slf4j:1.0.2")
    // database
    implementation("org.postgresql:postgresql:42.5.4")
    jooqGenerator("org.postgresql:postgresql:42.5.4")
    implementation("com.zaxxer:HikariCP:5.0.1")
    // classgraph
    implementation("io.github.classgraph:classgraph:4.8.154")
    // test
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

configurations { all { exclude(group = "org.slf4j", module = "slf4j-log4j12") } }

tasks.jacocoTestReport {
    group = "Reporting"
    description = "Generate Jacoco coverage reports after running tests."
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
    finalizedBy("jacocoTestCoverageVerification")
}

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

kotlin { jvmToolchain(17) }

application { mainClass.set("TheIslamicDiscordBot") }

// jooq
jooq {
    version.set("3.18.0")
    edition.set(JooqEdition.OSS)
    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(false)
            jooqConfiguration.apply {
                logging = Logging.WARN
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = getSecrete("DB_URL") ?: ""
                    user = getSecrete("DB_USER") ?: ""
                    password = getSecrete("DB_PASSWORD") ?: ""
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                        forcedTypes.addAll(
                            listOf(
                                ForcedType().apply {
                                    name = "varchar"
                                    includeExpression = ".*"
                                    includeTypes = "JSONB?"
                                },
                                ForcedType().apply {
                                    name = "INSTANT"
                                    includeExpression = ".*"
                                    includeTypes = "TIMESTAMP"
                                }))
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = true
                        isImmutablePojos = true
                        isFluentSetters = true
                    }
                    target.apply {
                        packageName = "io.github.yip.db"
                        directory = "src/main/java"
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}

fun getSecrete(key: String): String? {
    return if (System.getenv().containsKey(key)) {
        System.getenv(key)
    } else if (System.getProperties().containsKey(key)) {
        System.getProperty(key)
    } // check if config.json exists
    else if (File("config.json").exists()) {
        val config: JConfig = JConfig.build()
        if (config.contains(key)) {
            config[key]?.asString
        } else {
            null
        }
    } else {
        null
    }
}
