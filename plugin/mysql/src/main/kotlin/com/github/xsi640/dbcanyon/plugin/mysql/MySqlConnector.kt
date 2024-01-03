package com.github.xsi640.dbcanyon.plugin.mysql

import com.github.xsi640.dbcanyon.plugin.DatabaseContext
import com.github.xsi640.dbcanyon.plugin.DefaultSQLConnector
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

class MySqlConnector(
    override val ctx: DatabaseContext
) : DefaultSQLConnector() {
    override val driverClassName: String
        get() = "com.mysql.cj.jdbc.Driver"

    override fun connect(): Connection {
        val config = ctx.config
        val properties = Properties()
        if (config.username.isNotEmpty()) {
            properties["user"] = config.username
        }
        if (config.password.isNotEmpty()) {
            properties["password"] = config.password
        }
        if (config.parameters.isNotEmpty()) {
            config.parameters.forEach { (k, v) -> properties[k] = v }
        }
        if (!properties.containsKey("useSSL")) {
            properties["useSSL"] = "false"
        }
        Class.forName(driverClassName)
        return DriverManager.getConnection(config.url, properties)
    }
}