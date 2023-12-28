package com.github.xsi640.dbcanyon.plugin

const val QUERY_TABLE_ALIAS = "T_CANYON_QUERY"

abstract class DefaultSQLBuilder : SQLBuilder<DatabaseContext> {

    override fun createSchema(ctx: DatabaseContext, schema: String): String {
        return "CREATE SCHEMA IF NOT EXISTS $schema"
    }

    override fun prepare(
        ctx: DatabaseContext,
        sql: String,
        where: String,
        order: String,
        offset: Long,
        limit: Long
    ): String {
        val sb = StringBuilder("SELECT * FROM (")
        sb.append(sql).append(") AS $QUERY_TABLE_ALIAS")
        if (where.isNotEmpty()) {
            sb.append(" WHERE ").append(where)
        }
        if (order.isNotEmpty()) {
            sb.append(" ORDER BY ").append(order)
        }
        if (limit >= 0) {
            sb.append(" LIMIT ").append(limit)
        }
        if (offset > 0) {
            sb.append(" OFFSET ").append(offset)
        }
        return sb.toString()
    }

    override fun total(ctx: DatabaseContext, sql: String, where: String): String {
        val sb = StringBuilder("SELECT COUNT(*) FROM (")
        sb.append(sql).append(") AS $QUERY_TABLE_ALIAS")
        if (where.isNotEmpty()) {
            sb.append(" WHERE ").append(where)
        }
        return sb.toString()
    }
}