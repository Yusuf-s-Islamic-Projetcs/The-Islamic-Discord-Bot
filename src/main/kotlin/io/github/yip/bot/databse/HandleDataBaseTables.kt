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
import java.sql.SQLException
import java.util.*
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.jooq.impl.SQLDataType

object HandleDataBaseTables {
    val tables = ArrayList<String>()

    private fun handleTables(create: DSLContext) {
        addQuranReciterTable(create)

        try {
            checkTables(create)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    private fun checkTables(create: DSLContext) {
        // check if tables exist
        // if they don't exist, create them
        // if they exist, check if they need to be updated
        // finally if the tables name no longer exists within the list, delete them

        create
            .select()
            .from("information_schema.tables")
            .where("table_schema = 'public'")
            .fetch()
            .intoResultSet()
            .use { rs ->
                while (rs.next()) {
                   //TODO: check if tables need to be updated
                }
            }
    }

    private fun addQuranReciterTable(create: DSLContext) {
        val table =
            create
                .createTableIfNotExists("quran_reciter")
                .column("user_id", SQLDataType.BIGINT.nullable(false))
                .column("quran_reciter_id", SQLDataType.BIGINT.nullable(false))

        table.execute()

        tables.add("quran_reciter")
    }

    fun addTablesToDatabase(connection: Connection?) {
        val create = connection?.let { DSL.using(it) } ?: throw SQLException("Connection is null")
        handleTables(create)
    }
}
