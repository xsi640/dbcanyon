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
            where 1=1
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
        var sql = """
            select a.*,b.column_comment,c.constraint_name FROM information_schema.columns a
            left join (SELECT n.nspname AS schema_name, t.relname AS table_name, a.attname AS column_name, d.description AS column_comment
            FROM pg_namespace n
            JOIN pg_class t ON n.oid = t.relnamespace
            JOIN pg_attribute a ON t.oid = a.attrelid
            LEFT JOIN pg_description d ON t.oid = d.objoid AND a.attnum = d.objsubid) b 
            on a.table_schema=b.schema_name and a.table_name=b.table_name and a.column_name=b.column_name
            left join (SELECT column_name, constraint_schema as table_schema, table_name,constraint_name
            FROM information_schema.key_column_usage) c 
            on c.table_schema=b.schema_name and c.table_name=b.table_name and c.column_name=b.column_name
            WHERE 1=1
        """.trimIndent()
        if (schema.isNotEmpty()) {
            sql += " AND a.table_schema = '${schema}'"
        }
        if (table.isNotEmpty()) {
            sql += " AND a.table_name = '${table}'"
        }
        if (column.isNotEmpty()) {
            sql += " AND a.column_name = '${column}'"
        }
        ctx.execute(sql).use { rs ->
            val column = DefaultTableColumn()
            column.schemaName = schema
            column.tableName = table
            column.name = rs.getString("column_name")
            column.type = rs.getString("data_type")
            column.dataType = rs.getString("udt_name")
            column.defaultValue = rs.getString("column_default")
            column.autoIncrement = rs.getString("column_default").contains("nextval")
            column.primaryKey = rs.getString("constraint_name") != null
            column.length = rs.getLong("character_maximum_length")
            column.nullable = rs.getString("is_nullable") == "YES"
            column.generation = if (rs.getString("is_generated") != "NEVER") {
                rs.getString("generation_expression")
            } else {
                null
            }
            column.numericPrecision = rs.getLong("numeric_precision")
            column.numericScale = rs.getLong("numeric_scale")
            column.position = rs.getLong("ordinal_position")
            column.comment = rs.getString("column_comment")
            result.add(column)
        }
        return result
    }
}