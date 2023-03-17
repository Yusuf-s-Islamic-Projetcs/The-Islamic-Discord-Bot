package io.github.yip.bot.commands.admin

import io.github.ydwk.yde.builders.slash.SlashOption
import io.github.ydwk.yde.builders.slash.SlashOptionChoice
import io.github.ydwk.yde.builders.slash.SlashOptionType
import io.github.ydwk.yde.interaction.application.type.SlashCommand
import io.github.yip.bot.listeners.handler.slash.SlashCommandExtender
import io.github.yip.bot.user.UserIslamicInfoDatabase

class RegisterCommand : SlashCommandExtender {
    override fun onSlashCommand(event: SlashCommand) {
       val user = event.user ?: throw IllegalArgumentException("User is required")
        val quranReciter = event.getOption("quran_reciter")?.asLong ?: throw IllegalArgumentException("Quran reciter is required")
        val islamicSchool = event.getOption("islamic_school")?.asLong ?: throw IllegalArgumentException("Islamic school is required")

        if (!UserIslamicInfoDatabase.getUser(user.idAsLong)) {
            event.reply("You are already registered").setEphemeral(true).trigger()
            return
        } else {
            UserIslamicInfoDatabase.updateUserIslamicInfoDatabase(user.idAsLong, quranReciter, islamicSchool.toString())

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
            SlashOption("quran_reciter", "Quran reciter", SlashOptionType.INTEGER, true)
                .addChoices(recitersList),
            SlashOption("islamic_school", "Islamic school", SlashOptionType.INTEGER, true)
                .addChoices(
                    SlashOptionChoice("Shafi", 1),
                    SlashOptionChoice("Hanafi", 2),
                    SlashOptionChoice("Maliki", 3),
                    SlashOptionChoice("Hanbali", 4)
                )
        )
    }

    private val recitersList : MutableList<SlashOptionChoice>
        get() {
            val reciters = mutableListOf<SlashOptionChoice>()

            reciters.add(SlashOptionChoice("Abdul Rahman Al-Sudais", 1))

            return reciters
        }
}