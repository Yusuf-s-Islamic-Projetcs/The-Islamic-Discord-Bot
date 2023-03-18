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
import io.github.yip.db.tables.UserIslamicInfoSettings

object UserIslamicInfoDatabase {
    private val context =
        database?.context ?: throw IllegalStateException("Database context is null")

    fun updateUserIslamicInfoDatabase(userId: Long, quranReciterId: Long, islamicSchool: Long) {
        context
            .insertInto(
                UserIslamicInfoSettings.USER_ISLAMIC_INFO_SETTINGS,
                UserIslamicInfoSettings.USER_ISLAMIC_INFO_SETTINGS.USER_ID,
                UserIslamicInfoSettings.USER_ISLAMIC_INFO_SETTINGS.QURAN_RECITER_ID,
                UserIslamicInfoSettings.USER_ISLAMIC_INFO_SETTINGS.ISLAMIC_SCHOOL_ID)
            .values(userId, quranReciterId, islamicSchool)
            .onDuplicateKeyUpdate()
            .set(
                UserIslamicInfoSettings.USER_ISLAMIC_INFO_SETTINGS.QURAN_RECITER_ID, quranReciterId)
            .set(UserIslamicInfoSettings.USER_ISLAMIC_INFO_SETTINGS.ISLAMIC_SCHOOL_ID, islamicSchool)
            .execute()
    }

    /** Check if the user is registered */
    fun getUser(id: Long): Boolean {
        return context
            .select(UserIslamicInfoSettings.USER_ISLAMIC_INFO_SETTINGS)
            .from(UserIslamicInfoSettings.USER_ISLAMIC_INFO_SETTINGS)
            .where(UserIslamicInfoSettings.USER_ISLAMIC_INFO_SETTINGS.USER_ID.eq(id))
            .fetch()
            .intoResultSet()
            .use { rs -> rs.next() }
    }

    fun checkIfUserExists(slashEvent: SlashCommandEvent): Boolean {
        slashEvent.slash
        val user = slashEvent.slash.user ?: return false
        return getUser(user.idAsLong)
    }
}
