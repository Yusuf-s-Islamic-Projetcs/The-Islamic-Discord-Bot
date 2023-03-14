/*
 * Copyright 2022 YDWK inc.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package io.github.yip.bot.listeners.handler.slash

import io.github.ydwk.yde.builders.slash.SlashCommandBuilder
import io.github.ydwk.ydwk.YDWK
import io.github.ydwk.ydwk.evm.event.events.interaction.slash.SlashCommandEvent
import io.github.ydwk.ydwk.evm.listeners.InteractionListeners
import io.github.yip.bot.mainLogger

open class SlashHandler(private val ydwk: YDWK) : InteractionListeners {
    private val slashCommand: MutableMap<String, SlashCommandExtender> = HashMap()
    private val slashMutableList: MutableList<SlashCommandBuilder> = ArrayList()

    private fun addSlashCommands(command: SlashCommandExtender) {
        if (command.name().isEmpty()) {
            throw IllegalArgumentException("SlashCommandExtender name cannot be null")
        }

        slashCommand[command.name()] = command
        if (command.isGuildOnly()) {
            slashMutableList.add(
                SlashCommandBuilder(command.name(), command.description(), true).addOptions(command.options()))
        } else {
            slashMutableList.add(
                SlashCommandBuilder(command.name(), command.description(), false).addOptions(command.options()))
        }
    }

    fun registerSlashCommands(slashCommands: Collection<SlashCommandExtender>): SlashHandler {
        slashCommands.forEach { addSlashCommands(it) }
        return this
    }

    fun sendSlash() {
        if (slashMutableList.isNotEmpty()) {
            ydwk.slashBuilder.addSlashCommands(slashMutableList).build()
        } else {
            mainLogger.warn("No slash commands registered")
        }
    }

    override fun onSlashCommand(event: SlashCommandEvent) {
        slashCommand.forEach { (name, cmd) ->
            if (name == event.slash.name) {
                cmd.onSlashCommand(event.slash)
            }
        }
    }
}
