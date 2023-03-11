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
package io.github.yip.bot.databse

import java.sql.Connection

class HandleDataBaseTables(connection: Connection) {
    init {
        addTables(connection)
    }

    private fun addTables(connection: Connection) {
        addQuranReciterTable(connection)
    }

    private fun addQuranReciterTable(connection: Connection) {
        connection
            .createStatement()
            .executeUpdate(
                """
            CREATE TABLE IF NOT EXISTS quran_reciter (
                userId BIGINT NOT NULL,
                reciterId INT NOT NULL,
                UNIQUE (userId, reciterId)
            );
            """
                    .trimIndent())
    }
}
