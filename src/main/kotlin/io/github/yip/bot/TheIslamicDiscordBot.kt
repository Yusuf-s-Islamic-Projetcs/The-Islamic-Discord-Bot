/*
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
 */ 
package io.github.yip.bot

import io.github.realyusufismail.jconfig.JConfig
import io.github.ydwk.ydwk.BotBuilder.createDefaultBot
import io.github.ydwk.ydwk.YDWK
import io.github.ydwk.ydwk.evm.backend.event.on
import io.github.ydwk.ydwk.evm.event.events.interaction.slash.SlashCommandEvent
import io.github.yip.bot.databse.TheIslamicBotDatabase
import io.github.yip.bot.listeners.CoreEventListener
import io.github.yip.bot.listeners.handler.button.ButtonHandler
import io.github.yip.bot.listeners.handler.slash.AutoSlashAdder
import java.sql.SQLException
import kotlin.system.exitProcess
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class TheIslamicDiscordBot

val jConfig: JConfig = JConfig.build()

val mainLogger: Logger = LoggerFactory.getLogger(TheIslamicDiscordBot::class.java)

var database: TheIslamicBotDatabase? = null

suspend fun main() {
    val ydwk: YDWK =
        createDefaultBot(jConfig["TOKEN"]?.asString ?: throw Exception("Token not found")).build()

    ydwk.awaitReady().addEventListeners(CoreEventListener(), AutoSlashAdder(ydwk), ButtonHandler())

    database = TheIslamicBotDatabase()

    Runtime.getRuntime()
        .addShutdownHook(
            Thread {
                    mainLogger.info("Shutting down...")

                    if (database != null && !database!!.dataSource.isClosed) {
                        try {
                            mainLogger.info("Closing database connection...")
                            database!!.dataSource.connection.close()
                        } catch (e: SQLException) {
                            mainLogger.error("Error while closing database connection!")
                            e.printStackTrace()
                        }
                    }

                    try {
                        mainLogger.info("Closing bot...")
                        ydwk.shutdownAPI()
                    } catch (e: Exception) {
                        mainLogger.error("Error while closing bot!")
                        e.printStackTrace()
                    }

                    mainLogger.info("Shutdown complete!")

                    // stop the program
                    exitProcess(0)
                }
                .apply { name = "ShutdownHook" })
}
