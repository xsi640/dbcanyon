package com.github.xsi640.dbcanyon.plugin.mysql

import com.github.xsi640.dbcanyon.plugin.*

class MySqlDatabaseModel(override val ctx: DatabaseContext) : DefaultDatabaseModel(ctx) {
    override val metadataQuote: Char
        get() = '`'

    override fun tables(database: String, schema: String, table: String): List<Table> {
        val result = mutableListOf<Table>()
        var sql = "SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE'"
        if (schema.isNotEmpty()) {
            sql += " AND TABLE_SCHEMA = '${schema}'"
        }
        if (table.isNotEmpty()) {
            sql += " AND TABLE_NAME = '${table}'"
        }
        ctx.execute(sql).use { rs ->
            while (rs.next()) {
                val table = MySqlTable(
                    engine = rs.getString("ENGINE") ?: "",
                    version = rs.getString("VERSION") ?: "",
                    rowFormat = rs.getString("ROW_FORMAT") ?: "",
                    collation = rs.getString("TABLE_COLLATION") ?: "",
                )
                table.schemaName = rs.getString("TABLE_SCHEMA")
                table.name = rs.getString("TABLE_NAME")
                table.comment = rs.getString("TABLE_COMMENT") ?: ""
                table.columnList = tableColumns(database, schema, table.name, "").toMutableList()
                result.add(table)
            }
        }
        return result
    }

    override fun tableColumns(database: String, schema: String, table: String, column: String): List<TableColumn> {
        val result = mutableListOf<TableColumn>()
        var sql = "SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE 1=1 "
        if (schema.isNotEmpty()) {
            sql += " AND TABLE_SCHEMA = '${schema}'"
        }
        if (table.isNotEmpty()) {
            sql += " AND TABLE_NAME = '${table}'"
        }
        if (column.isNotEmpty()) {
            sql += " AND COLUMN_NAME = '${column}'"
        }
        ctx.execute(sql).use { rs ->
            while (rs.next()) {
                val tableColumn = MySqlTableColumn(
                    charset = rs.getString("CHARACTER_SET_NAME") ?: "",
                    collation = rs.getString("COLLATION_NAME") ?: "",
                )
                tableColumn.schemaName = schema
                tableColumn.tableName = table
                tableColumn.name = rs.getString("COLUMN_NAME")
                tableColumn.type = rs.getString("COLUMN_TYPE")
                tableColumn.dataType = rs.getString("DATA_TYPE")
                tableColumn.defaultValue = rs.getString("COLUMN_DEFAULT")
                tableColumn.autoIncrement = rs.getString("EXTRA").contains("auto_increment")
                tableColumn.primaryKey = rs.getString("COLUMN_KEY") == "PRI"
                tableColumn.length = rs.getLong("CHARACTER_MAXIMUM_LENGTH")
                tableColumn.nullable = rs.getString("IS_NULLABLE") == "YES"
                tableColumn.generation = if (rs.getString("EXTRA").contains("GENERATED")) {
                    rs.getString("GENERATION_EXPRESSION") ?: ""
                } else {
                    null
                }
                tableColumn.numericPrecision = rs.getLong("NUMERIC_PRECISION")
                tableColumn.numericScale = rs.getLong("NUMERIC_SCALE")
                tableColumn.position = rs.getLong("ORDINAL_POSITION")
                tableColumn.comment = rs.getString("COLUMN_COMMENT")
                result.add(tableColumn)
            }
        }
        return result
    }
}

class MySqlTable(
    var engine: String,
    var version: String,
    var rowFormat: String,
    var collation: String,
) : DefaultTable()

class MySqlTableColumn(
    var charset: String,
    var collation: String
) : DefaultTableColumn()