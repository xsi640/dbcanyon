package com.github.xsi640.canyon.core

import com.github.xsi640.dbcanyon.plugin.ConnectionConfig
import com.github.xsi640.dbcanyon.plugin.DatabaseType
import com.github.xsi640.dbcanyon.plugin.DefaultDatabaseContext
import org.junit.jupiter.api.Test

class Test {
    @Test
    fun test() {
       val ctx=  DefaultDatabaseContext(
            ConnectionConfig(
                url = "jdbc:mysql://10.10.3.28:3306/test",
                username = "root",
                password = "123456",
                type = DatabaseType.MYSQL
            )
        )
        ctx.databaseModel.databases(ctx).forEach {
            println(it.name)
        }
    }
}