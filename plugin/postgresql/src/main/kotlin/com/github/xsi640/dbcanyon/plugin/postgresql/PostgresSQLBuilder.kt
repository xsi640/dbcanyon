package com.github.xsi640.dbcanyon.plugin.postgresql

import com.github.xsi640.dbcanyon.plugin.DatabaseContext
import com.github.xsi640.dbcanyon.plugin.DefaultSQLBuilder

class PostgresSQLBuilder(
    override val ctx: DatabaseContext
) : DefaultSQLBuilder() {
    override fun createTable(schema: String, table: String): String {
        TODO()
    }
}