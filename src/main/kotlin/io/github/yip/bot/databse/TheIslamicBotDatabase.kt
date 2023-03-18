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

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.yip.bot.databse.HandleDataBaseTables.addTablesToDatabase
import io.github.yip.bot.jConfig
import java.sql.SQLException
import java.util.*
import kotlin.Exception
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class TheIslamicBotDatabase {
    private val logger: Logger = LoggerFactory.getLogger(TheIslamicBotDatabase::class.java)
    private var config: HikariConfig = HikariConfig()
    var dataSource: HikariDataSource

    init {
        val props = Properties()
        props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource")
        props.setProperty(
            "dataSource.user",
            jConfig["DB_USER"]?.asString ?: throw Exception("Database user not found"))
        props.setProperty(
            "dataSource.password",
            jConfig["DB_PASSWORD"]?.asString ?: throw Exception("Database password not found"))
        props.setProperty(
            "dataSource.databaseName",
            jConfig["DB_NAME"]?.asString ?: throw Exception("Database name not found"))
        props.setProperty(
            "dataSource.portNumber",
            jConfig["DB_PORT"]?.asString ?: throw Exception("Database port not found"))
        props.setProperty(
            "dataSource.serverName",
            jConfig["DB_HOST"]?.asString ?: throw Exception("Database host not found"))
        props.setProperty("maximumPoolSize", "10")
        props.setProperty("minimumIdle", "5")
        props.setProperty("idleTimeout", "30000")

        config = HikariConfig(props)
        dataSource = HikariDataSource(config)

        logger.info(
            "Database connection established, will now attempt to create tables or update them")

        try {
            addTablesToDatabase(connection)
        } catch (e: Exception) {
            logger.error("Error creating tables", e)
        }
    }

    /** The actual connection to the database */
    private val connection
        get() =
            try {
                dataSource.connection
            } catch (e: SQLException) {
                logger.error("Error getting connection", e)
                null
            }

    /** Used to get tables from the database */
    val context = connection?.let { DSL.using(it, SQLDialect.POSTGRES) }

    fun close() {
        dataSource.close()
    }
}
