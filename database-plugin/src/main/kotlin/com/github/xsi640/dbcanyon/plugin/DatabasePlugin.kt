package com.github.xsi640.dbcanyon.plugin

import java.sql.Connection

interface DatabasePlugin {
    val type: DatabaseType
    fun model(): DatabaseModel<DatabaseContext>
    fun builder(): SQLBuilder<DatabaseContext>
    fun connector(): SQLConnector<DatabaseContext>
}

interface DatabaseModel<C : DatabaseContext> {
    fun databases(ctx: C): List<Database>
    fun schemas(ctx: C, database: String): List<Schema>
    fun tables(ctx: C, database: String, schema: String): List<Table> {
        return tables(ctx, database, schema, "")
    }

    fun tables(ctx: C, database: String, schema: String, table: String): List<Table>
    fun tableColumns(ctx: C, database: String, schema: String, table: String): List<TableColumn> {
        return tableColumns(ctx, database, schema, table, "")
    }

    fun tableColumns(ctx: C, database: String, schema: String, table: String, column: String): List<TableColumn>
    fun metaDataNames(vararg name: String): String
}

interface SQLBuilder<C : DatabaseContext> {
    fun prepare(ctx: C, sql: String, where: String, order: String, offset: Long, limit: Long): String
    fun total(ctx: C, sql: String, where: String): String
    fun createSchema(ctx: C, schema: String): String
    fun createTable(ctx: C, schema: String, table: String): String
}

interface SQLConnector<C : DatabaseContext> {
    val driverClassName: String
    fun connect(ctx: C): Connection
}