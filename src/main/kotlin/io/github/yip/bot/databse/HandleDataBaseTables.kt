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

import com.zaxxer.hikari.HikariDataSource
import io.github.yip.bot.mainLogger
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.jooq.impl.DSL.constraint
import org.jooq.impl.SQLDataType

object HandleDataBaseTables {

    fun handleTables(create: DSLContext) {
        addQuranReciterTable(create)
    }

    private fun addQuranReciterTable(create: DSLContext) {
        create.createTableIfNotExists("quran_reciter")
            .column("user_id", SQLDataType.BIGINT.nullable(false))
            .column("quran_reciter_id", SQLDataType.BIGINT.nullable(false))
            .constraints(
                constraint("quran_reciter_pk").primaryKey("user_id"),
                constraint("quran_reciter_fk").foreignKey("quran_reciter_id").references("quran_reciter", "id")
            )
            .execute()
    }

    fun addTablesToDatabase(dataSource: HikariDataSource) {
        val create = dataSource.connection.use { DSL.using(it) }
        handleTables(create)
    }
}
