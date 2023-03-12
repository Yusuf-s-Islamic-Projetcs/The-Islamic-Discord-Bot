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

import io.github.yip.bot.mainLogger
import org.jooq.DSLContext
import org.jooq.impl.DSL.constraint
import org.jooq.impl.SQLDataType

class HandleDataBaseTables(dslContext: DSLContext) {

    init {
        mainLogger.info("Creating tables")

        addQuranReciterTable(dslContext)
    }

    private fun addQuranReciterTable(dslContext: DSLContext) {
        try {
            dslContext.createTableIfNotExists("quran_reciter")
                .column("user_id", SQLDataType.BIGINT.identity(true))
                .column("reciter_id", SQLDataType.BIGINT)
                .constraints(
                    constraint("pk_id").primaryKey("user_id")
                )
                .executeAsync { mainLogger.info("Created table quran_reciter") }
        } catch (e: Exception) {
            mainLogger.error("Error creating table quran_reciter", e)
        }
    }
}
