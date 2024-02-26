package com.github.xsi640.dbcanyon.plugin.postgresql

import com.github.xsi640.dbcanyon.plugin.DatabaseContext
import com.github.xsi640.dbcanyon.plugin.DefaultSQLConnector

class PostgresConnector(
    override val ctx: DatabaseContext
) : DefaultSQLConnector() {
}