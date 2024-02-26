package com.github.xsi640.dbcanyon.plugin

import java.sql.Connection
import java.sql.DriverManager
import java.util.Properties

abstract class DefaultSQLConnector : SQLConnector {

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
        Class.forName(ctx.driver.className)
        return DriverManager.getConnection(config.url, properties)
    }
}