package com.github.xsi640.dbcanyon.plugin

class ConnectionConfig(
    var url: String = "",
    var database: String = "",
    var schema: String = "",
    var username: String = "",
    var password: String = "",
    var type: DatabaseType = DatabaseType.POSTGRESQL,
    var host: String = "",
    var port: String = "",
    var ssh: SSHConfig? = null,
    var sid: String = "",
    var parameters: Map<String, String> = emptyMap(),
)

class SSHConfig(
    var remoteIP: String = "",
    var remotePort: String = "",
    var localPort: String = "",
    var username: String = "",
    var password: String = "",
    var key: ByteArray? = null,
    var passphrase: ByteArray? = null
)

enum class DatabaseType(val code: Int) {
    POSTGRESQL(0),
    MYSQL(1),
    CLICKHOUSE(2),
    H2(3),
    ORACLE(4),
    SQLSERVER(5),
    SQLITE(6),
    MARIADB(7),
    DM(8),
    PRESTO(9),
    DB2(10),
    REDIS(11),
    HIVE(12),
    MONGODB(13);


    companion object {
        fun codeOf(code: Int): DatabaseType {
            return entries.first { it.code == code }
        }
    }
}

interface Database {
    var name: String
    var schemas: List<Schema>
    var comment: String
}

open class DefaultDatabase(
    override var name: String = ""
) : Database {
    override var schemas: List<Schema> = emptyList()
    override var comment: String = ""
}

interface Schema {
    var databaseName: String
    var name: String
    var comment: String
}

open class DefaultSchema(
    override var databaseName: String = "",
    override var name: String = "",
    override var comment: String = ""
) : Schema

interface Table {
    var name: String
    var comment: String
    var columnList: MutableList<TableColumn>
}

open class DefaultTable : Table {
    override var name: String = ""
    override var comment: String = ""
    override var columnList: MutableList<TableColumn> = mutableListOf()
}

interface TableColumn {
    var name: String
    var dataType: String
    var defaultValue: String
    var autoIncrement: Boolean
    var primaryKey: Boolean
    var length: Int
    var nullable: Boolean
    var generated: Boolean
    var numericPrecision: Int
    var numericScale: Int
    var comment: String
    var charset: String
}

open class DefaultTableColumn : TableColumn {
    override var name: String = ""
    override var dataType: String = ""
    override var defaultValue: String = ""
    override var autoIncrement: Boolean = false
    override var primaryKey: Boolean = false
    override var length: Int = 0
    override var nullable: Boolean = false
    override var generated: Boolean = false
    override var numericPrecision: Int = 0
    override var numericScale: Int = 0
    override var comment: String = ""
    override var charset: String = ""
}