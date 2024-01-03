package com.github.xsi640.dbcanyon.plugin

abstract class DefaultDatabaseModel(
    override val ctx: DatabaseContext
) : DatabaseModel {

    override val metadataQuote: Char
        get() = '"'

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

    override fun metaDataNames(vararg name: String): String {
        val quote = metadataQuote.toString()
        return name.joinToString(separator = ".", prefix = quote, postfix = quote, transform = {
            it.trim(*maybeQuote)
        })
    }

    companion object {
        val maybeQuote = charArrayOf('`', '"', '\'')
        val TABLE_TYPES = arrayOf("TABLE", "SYSTEM TABLE")
        val SCHEMA_TABLE = "SELECT TABLE_NAME, TABLE_COMMENT FROM information_schema.tables WHERE table_schema=?"
        val SCHEMA_COLUMN = """
            SELECT COLUMN_NAME, COLUMN_DEFAULT ,IS_NULLABLE ,DATA_TYPE, CHARACTER_MAXIMUM_LENGTH ,NUMERIC_PRECISION ,NUMERIC_SCALE,COLUMN_TYPE ,COLUMN_KEY ,EXTRA 
            FROM information_schema.columns
            WHERE table_schema=? AND table_name=? AND ;
        """.trimIndent()
    }
}