package com.github.xsi640.dbcanyon.plugin.postgresql

import com.github.xsi640.dbcanyon.plugin.*

class PostgresPlugin : DatabasePlugin {
    override val type: DatabaseType
        get() = DatabaseType.POSTGRESQL

    override fun model(ctx: DatabaseContext): DatabaseModel {
        return PostgresDatabaseModel(ctx)
    }

    override fun builder(ctx: DatabaseContext): SQLBuilder {
        return PostgresSQLBuilder(ctx)
    }

    override fun connector(ctx: DatabaseContext): SQLConnector {
        return PostgresConnector(ctx)
    }
}