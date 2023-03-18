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
package io.github.yip.bot.commands.admin

import io.github.classgraph.ClassGraph
import io.github.ydwk.yde.builders.slash.SlashOption
import io.github.ydwk.yde.builders.slash.SlashOptionChoice
import io.github.ydwk.yde.builders.slash.SlashOptionType
import io.github.ydwk.yde.interaction.application.type.SlashCommand
import io.github.yip.bot.listeners.handler.slash.SlashCommandExtender
import io.github.yip.bot.quran.reciters.Reciter
import io.github.yip.bot.user.UserIslamicInfoDatabase

class RegisterCommand : SlashCommandExtender {
    override fun onSlashCommand(event: SlashCommand) {
        val user =
            event.user
                ?: event.member?.user ?: throw IllegalStateException("Something is seriously wrong")

        val quranReciterOption =
            event.getOption("quran_reciter")
                ?: throw IllegalStateException("Quran reciter cannot be null")

        val islamicSchoolOption =
            event.getOption("islamic_school")
                ?: throw IllegalStateException("Islamic school cannot be null")

        if (UserIslamicInfoDatabase.getUser(user.idAsLong)) {
            event.reply("You are already registered").setEphemeral(true).trigger()
            return
        } else {
            UserIslamicInfoDatabase.updateUserIslamicInfoDatabase(
                user.idAsLong,
                quranReciterOption.asDouble.toLong(),
                islamicSchoolOption.asDouble.toLong())

            event.reply("You have successfully registered").setEphemeral(true).trigger()
        }
    }

    override fun name(): String {
        return "register"
    }

    override fun description(): String {
        return "Register yourself in order to use the bot"
    }

    override fun isGuildOnly(): Boolean {
        return false
    }

    override fun options(): MutableList<SlashOption> {
        return mutableListOf(
            SlashOption("quran_reciter", "Quran reciter", SlashOptionType.NUMBER, true)
                .addChoices(recitersList),
            SlashOption("islamic_school", "Islamic school", SlashOptionType.NUMBER, true)
                .addChoices(
                    // This has the islamic school id
                    SlashOptionChoice("Shafi", 1),
                    SlashOptionChoice("Hanafi", 2),
                    SlashOptionChoice("Maliki", 3),
                    SlashOptionChoice("Hanbali", 4)))
    }

    private val recitersList: MutableList<SlashOptionChoice>
        get() {
            val reciters = mutableListOf<SlashOptionChoice>()
            ClassGraph().enableAllInfo().scan().use { scanResult ->
                val reciterClasses = scanResult.getClassesImplementing(Reciter::class.java.name)
                reciterClasses.forEach { reciterClass ->
                    val reciter = reciterClass.loadClass().getConstructor().newInstance() as Reciter
                    reciters.add(SlashOptionChoice(reciter.name, reciter.reciterId))
                }
            }
            return reciters
        }
}
