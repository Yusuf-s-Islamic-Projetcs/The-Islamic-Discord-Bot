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
        // user settings table
        val columns = mutableMapOf<String, DataType<*>>()
        columns["user_id"] = SQLDataType.BIGINT.nullable(false)
        columns["quran_reciter_id"] = SQLDataType.BIGINT.nullable(false)
        columns["islamic_school_id"] = SQLDataType.BIGINT.nullable(false)

        val ut = create.createTableIfNotExists("user_islamic_info_settings")
        columns.forEach { (key, value) -> ut.column(key, value) }
        ut.primaryKey("user_id")
        ut.execute()

        HandleDataBaseTables.tables.add("user_islamic_info_settings")
        HandleDataBaseTables.tablesColumns["user_islamic_info_settings"] = columns
    }
}
