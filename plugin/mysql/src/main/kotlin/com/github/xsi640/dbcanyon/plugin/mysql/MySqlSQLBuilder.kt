package com.github.xsi640.dbcanyon.plugin.mysql

import com.github.xsi640.dbcanyon.plugin.DatabaseContext
import com.github.xsi640.dbcanyon.plugin.DefaultSQLBuilder

class MySqlSQLBuilder(
    override val ctx: DatabaseContext
) : DefaultSQLBuilder() {
    override fun createTable(schema: String, table: String): String {
        val model = ctx.databaseModel
        var result = ""
        ctx.execute("SHOW CREATE TABLE ${model.metaDataNames(schema, table)}").use { rs ->
            if (rs.next()) {
                result = rs.getString(2)
            }
        }
        return result
    }
}