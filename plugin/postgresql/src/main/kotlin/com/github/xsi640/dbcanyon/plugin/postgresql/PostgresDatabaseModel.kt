package com.github.xsi640.dbcanyon.plugin.postgresql

import com.github.xsi640.dbcanyon.plugin.*

class PostgresDatabaseModel(
    override val ctx: DatabaseContext
) : DefaultDatabaseModel(ctx) {
    override val metadataQuote: Char
        get() = '"'

    override fun tables(database: String, schema: String, table: String): List<Table> {
        val result = mutableListOf<Table>()
        var sql = """
            SELECT
                tablename AS table_name,
                schemaname as schema_name,
                description as table_comment
            FROM
                pg_tables
                LEFT JOIN pg_description ON pg_tables.tablename::regclass = pg_description.objoid
            where 1=1 and
        """.trimIndent()
        if (schema.isNotEmpty()) {
            sql += " AND schemaname = '${schema}'"
        }
        if (table.isNotEmpty()) {
            sql += " AND tablename = '${table}'"
        }
        ctx.execute(sql).use { rs ->
            while (rs.next()) {
                val table = DefaultTable()
                table.name = rs.getString("table_name")
                table.schemaName = rs.getString("schema_name")
                table.comment = rs.getString("table_comment") ?: ""
                table.columnList = tableColumns(database, schema, table.name, "").toMutableList()
                result.add(table)
            }
        }
        return result
    }

    override fun tableColumns(database: String, schema: String, table: String, column: String): List<TableColumn> {
        val result = mutableListOf<TableColumn>()
        TODO()
        return result
    }
}