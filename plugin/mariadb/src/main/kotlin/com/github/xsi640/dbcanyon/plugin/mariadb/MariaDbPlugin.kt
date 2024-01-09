package com.github.xsi640.dbcanyon.plugin.mariadb

import com.github.xsi640.dbcanyon.plugin.*

class MariaDbPlugin : DatabasePlugin {
    override val type: DatabaseType
        get() = DatabaseType.MARIADB

    override fun model(ctx: DatabaseContext): DatabaseModel {
        return MariaDbDatabaseModel(ctx)
    }

    override fun builder(ctx: DatabaseContext): SQLBuilder {
        return MariaDbSQLBuilder(ctx)
    }

    override fun connector(ctx: DatabaseContext): SQLConnector {
        return MariaDbConnector(ctx)
    }


}