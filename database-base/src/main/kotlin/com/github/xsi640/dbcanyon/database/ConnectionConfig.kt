package com.github.xsi640.dbcanyon.database

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
            return values().first { it.code == code }
        }
    }
}