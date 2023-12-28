package com.github.xsi640.dbcanyon.plugin

abstract class DefaultDatabaseModel(
    override val ctx: DatabaseContext
) : DatabaseModel {
    override fun databases(): List<Database> {
        val result = mutableListOf<Database>()
        ctx.connection.metaData.catalogs.use { rs ->
            while (rs.next()) {
                result.add(
                    DefaultDatabase(
                        name = rs.getString(1),
                    )
                )
            }
        }
        return result
    }

    override fun schemas(database: String): List<Schema> {
        val result = mutableListOf<Schema>()
        ctx.connection.metaData.schemas.use { rs ->
            while (rs.next()) {
                result.add(
                    DefaultSchema(
                        name = rs.getString(1),
                    )
                )
            }
        }
        return result
    }

    override fun tables(database: String, schema: String, table: String): List<Table> {
        TODO("Not yet implemented")
    }

    override fun tableColumns(
        database: String,
        schema: String,
        table: String,
        column: String
    ): List<TableColumn> {
        TODO("Not yet implemented")
    }

    override fun metaDataNames(vararg name: String): String {
        return name.joinToString(separator = ".", prefix = "`", postfix = "`", transform = {
            it.trim('`', '"', '\'')
        })
    }
}