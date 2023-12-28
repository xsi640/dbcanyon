package com.github.xsi640.dbcanyon.plugin.mysql

import com.github.xsi640.dbcanyon.plugin.DatabaseContext
import com.github.xsi640.dbcanyon.plugin.DefaultSQLBuilder

class MySqlSQLBuilder : DefaultSQLBuilder() {
    override fun createTable(ctx: DatabaseContext, schema: String, table: String): String {
        val model = ctx.databaseModel
        var result = ""
        ctx.execute("show create table ${model.metaDataNames(schema, table)}").use { rs ->
            if (rs.next()) {
                result = rs.getString(2)
            }
        }
        return result
    }
}