package io.github.yip.bot.databse

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.yip.bot.jConfig
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Database {
    private val logger: Logger = LoggerFactory.getLogger(Database::class.java)
    private val config: HikariConfig = HikariConfig()
    private var dataSource: HikariDataSource
    private var dslContext: DSLContext

    init {
        config.jdbcUrl = jConfig["DB_URL"]?.asString ?: throw Exception("JDBC URL not found")
        config.username = jConfig["DB_USER"]?.asString ?: throw Exception("Username not found")
        config.password = jConfig["DB_PASSWORD"]?.asString ?: throw Exception("Password not found")
        config.driverClassName = "org.postgresql.Driver"
        config.maximumPoolSize = 10
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()

        dataSource = HikariDataSource(config)
        dslContext = DSL.using(dataSource, SQLDialect.POSTGRES)

        logger.info("Database connection established")
    }

    fun getDataSource(): HikariDataSource {
        return dataSource
    }

    fun close() {
        dataSource.close()
    }
}