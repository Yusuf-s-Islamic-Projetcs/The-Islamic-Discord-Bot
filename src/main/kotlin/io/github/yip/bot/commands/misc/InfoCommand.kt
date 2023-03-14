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
package io.github.yip.bot.commands.misc

import io.github.ydwk.yde.interaction.application.type.SlashCommand
import io.github.ydwk.ydwk.util.ydwk
import io.github.yip.bot.listeners.handler.slash.SlashCommandExtender

class InfoCommand : SlashCommandExtender {
    override fun onSlashCommand(event: SlashCommand) {
        val embed = event.ydwk.embedBuilder
        val guild = event.guild ?: return

        embed.setTitle("Info about The Islamic Bot")
        embed.setDescription(
            "The Islamic Bot is a bot that provides Islamic information and services to the Discord community.")
    }

    override fun name(): String {
        TODO("Not yet implemented")
    }

    override fun description(): String {
        TODO("Not yet implemented")
    }

    override fun isGuildOnly(): Boolean {
        TODO("Not yet implemented")
    }
}
