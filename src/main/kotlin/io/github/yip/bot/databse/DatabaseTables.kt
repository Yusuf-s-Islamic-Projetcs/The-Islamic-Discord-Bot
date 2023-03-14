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

import org.jooq.DSLContext
import org.jooq.DataType
import org.jooq.impl.SQLDataType

class DatabaseTables(create: DSLContext) {

    init {
        // quran tables
        val columns = mutableMapOf<String, DataType<*>>()
        columns["user_id"] = SQLDataType.BIGINT.nullable(false)
        columns["quran_reciter_id"] = SQLDataType.BIGINT.nullable(false)

        val table = create.createTableIfNotExists("quran_reciter")

        for (column in columns) {
            table.column(column.key, column.value)
        }

        table.execute()

        HandleDataBaseTables.tables.add("quran_reciter")
        HandleDataBaseTables.tablesColumns["quran_reciter"] = columns
    }
}
