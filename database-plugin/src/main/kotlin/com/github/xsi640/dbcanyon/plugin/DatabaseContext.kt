package com.github.xsi640.dbcanyon.plugin

import java.io.Closeable
import java.sql.Connection
import java.sql.ResultSet
import java.util.ServiceLoader

interface DatabaseContext : Closeable {
    val config: ConnectionConfig

    var sqlBuilder: SQLBuilder<DatabaseContext>
    var sqlConnector: SQLConnector<DatabaseContext>
    var databaseModel: DatabaseModel<DatabaseContext>
    val connection: Connection
    fun execute(sql: String, vararg arg: Any?): ResultSet
}

class DefaultDatabaseContext(
    override val config: ConnectionConfig
) : DatabaseContext {

    override var databaseModel = plugins[config.type]!!.model()
    override var sqlBuilder = plugins[config.type]!!.builder()
    override var sqlConnector = plugins[config.type]!!.connector()
    override val connection by lazy {
        sqlConnector.connect(this)
    }

    override fun execute(sql: String, vararg arg: Any?): ResultSet {
        return connection.createStatement().executeQuery(sql)
    }

    override fun close() {
        connection.close()
    }

    companion object {
        val plugins = ServiceLoader.load(DatabasePlugin::class.java)
            .associateBy { it.type }
    }
}