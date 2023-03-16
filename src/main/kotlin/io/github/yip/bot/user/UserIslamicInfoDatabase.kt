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
package io.github.yip.bot.user

import io.github.ydwk.ydwk.evm.event.events.interaction.slash.SlashCommandEvent
import io.github.yip.bot.database
import io.github.yip.db.tables.UserSettings

object UserIslamicInfoDatabase {
    val context = database?.context ?: throw IllegalStateException("Database context is null")

    fun checkIfUserExists(slashEvent: SlashCommandEvent): Boolean {
        val slash = slashEvent.slash
        var regested: Boolean

        // context.select
        context
            .select(UserSettings.USER_SETTINGS)
            .from(UserSettings.USER_SETTINGS)
            .where(UserSettings.USER_SETTINGS.USER_ID.eq(slash.user?.idAsLong ?: 0))
            .fetch()
            .intoResultSet()
            .use { rs -> regested = rs.next() }

        return if (!regested) {
            slash
                .reply("Please register your information first, use /register")
                .setEphemeral(true)
                .trigger()
            false
        } else {
            true
        }
    }
}
