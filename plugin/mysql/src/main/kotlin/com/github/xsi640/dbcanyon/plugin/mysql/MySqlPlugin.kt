package com.github.xsi640.dbcanyon.plugin.mysql

import com.github.xsi640.dbcanyon.plugin.*

class MySqlPlugin : DatabasePlugin {

    private var connector = MySqlConnector()
    private var sqlBuilder = MySqlSQLBuilder()
    private var model = MySqlDatabaseModel()

    override val type: DatabaseType
        get() = DatabaseType.MYSQL

    override fun model(): DatabaseModel<DatabaseContext> {
        return model
    }

    override fun builder(): SQLBuilder<DatabaseContext> {
        return sqlBuilder
    }

    override fun connector(): SQLConnector<DatabaseContext> {
        return connector
    }
}