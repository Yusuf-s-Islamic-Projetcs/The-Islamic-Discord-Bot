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
import io.github.ydwk.ydwk.YDWKInfo
import io.github.ydwk.ydwk.util.ydwk
import io.github.ydwk.ydwk.ws.util.formatInstant
import io.github.yip.bot.listeners.handler.slash.SlashCommandExtender
import java.awt.Color

class InfoCommand : SlashCommandExtender {
    override fun onSlashCommand(event: SlashCommand) {
        val embed = event.ydwk.embedBuilder

        embed.setTitle("Info about The Islamic Bot")
        embed.setDescription(
            "The Islamic Bot is a bot that provides Islamic information and services to the Discord community.")
        embed.setColor(Color.decode("#00A86B"))
        embed.addField("Guilds", event.yde.getGuilds().size.toString(), true)
        embed.addField("Users", event.yde.getUsers().size.toString(), true)
        embed.addField("Channels", event.yde.getChannels().size.toString(), true)
        embed.addField("Uptime", formatInstant(event.ydwk.uptime), true)
        embed.addField("Bot Version", "1.0-SNAPSHOT", true)
        embed.addField("YDWK Version", YDWKInfo.YDWK_VERSION.getUrl(), true)

        event.reply(embed.build()).trigger()
    }

    override fun name(): String {
        return "info"
    }

    override fun description(): String {
        return "Get info about the bot"
    }

    override fun isGuildOnly(): Boolean {
        return false
    }
}
