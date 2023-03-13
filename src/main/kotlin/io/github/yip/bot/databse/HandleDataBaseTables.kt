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
    private val tables = ArrayList<String>()

    private fun handleTables(create: DSLContext) {
        addQuranReciterTable(create)

        try {
            checkTables(create)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    private fun checkTables(create: DSLContext) {
        create
            .select()
            .from("information_schema.tables")
            .where("table_schema = 'public'")
            .fetch()
            .intoResultSet()
            .use { rs ->
                val tableNames = ArrayList<String>()
                while (rs.next()) {
                    tableNames.add(rs.getString("table_name"))
                }
                for (tableName in tableNames) {
                    if (!tables.contains(tableName)) {
                        create.dropTable(tableName).execute()
                    }
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
