package com.github.xsi640.dbcanyon.plugin.mariadb

import com.github.xsi640.dbcanyon.plugin.DatabaseContext
import com.github.xsi640.dbcanyon.plugin.DefaultSQLConnector

class MariaDbConnector(
    override val ctx: DatabaseContext
) : DefaultSQLConnector() {
    override val driverClassName: String
        get() = "org.mariadb.jdbc.Driver"
}