package com.github.xsi640.dbcanyon.plugin.mysql

import com.github.xsi640.dbcanyon.plugin.*

class MySqlPlugin : DatabasePlugin {

    override val type: DatabaseType
        get() = DatabaseType.MYSQL

    override fun model(ctx: DatabaseContext): DatabaseModel {
        return MySqlDatabaseModel(ctx)
    }

    override fun builder(ctx: DatabaseContext): SQLBuilder {
        return MySqlSQLBuilder(ctx)
    }

    override fun connector(ctx: DatabaseContext): SQLConnector {
        return MySqlConnector(ctx)
    }
}