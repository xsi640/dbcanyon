package com.github.xsi640.dbcanyon.plugin

import java.sql.Connection

interface DatabasePlugin {
    val type: DatabaseType

    fun drivers(): List<DatabaseDriver>
    fun model(ctx: DatabaseContext): DatabaseModel
    fun builder(ctx: DatabaseContext): SQLBuilder
    fun connector(ctx: DatabaseContext): SQLConnector
}

interface DatabaseModel {
    val ctx: DatabaseContext
    val metadataQuote: Char
    fun databases(): List<Database>
    fun schemas(database: String): List<Schema>
    fun tables(database: String, schema: String): List<Table> {
        return tables(database, schema, "")
    }

    fun tables(database: String, schema: String, table: String): List<Table>
    fun tableColumns(database: String, schema: String, table: String): List<TableColumn> {
        return tableColumns(database, schema, table, "")
    }

    fun tableColumns(database: String, schema: String, table: String, column: String): List<TableColumn>
    fun metaDataNames(vararg name: String): String
}

interface SQLBuilder {
    val ctx: DatabaseContext
    fun prepare(sql: String, where: String, order: String, offset: Long, limit: Long): String
    fun total(sql: String, where: String): String
    fun createSchema(schema: String): String
    fun createTable(schema: String, table: String): String
}

interface SQLConnector {
    val ctx: DatabaseContext
    fun connect(): Connection
}

interface DatabaseDriver {
    val name: String
    val url: String
    val parameters: Map<String, String>
    val files: Set<String>
    val className: String
    val default: Boolean
}