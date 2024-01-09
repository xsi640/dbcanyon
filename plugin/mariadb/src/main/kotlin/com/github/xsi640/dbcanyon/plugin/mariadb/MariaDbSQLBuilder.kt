package com.github.xsi640.dbcanyon.plugin.mariadb

import com.github.xsi640.dbcanyon.plugin.*

class MariaDbSQLBuilder(
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