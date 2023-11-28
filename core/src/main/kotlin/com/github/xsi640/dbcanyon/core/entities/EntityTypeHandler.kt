package com.github.xsi640.dbcanyon.core.entities

import com.github.xsi640.dbcanyon.core.extends.Json.toJsonString
import com.github.xsi640.dbcanyon.core.extends.Json.toObject
import org.apache.ibatis.type.BaseTypeHandler
import org.apache.ibatis.type.JdbcType
import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet

class EntityTypeHandler(
    val type: Class<Any?>
) : BaseTypeHandler<Any?>() {

    private fun String?.parse(): Any? = if (this.isNullOrEmpty()) {
        null
    } else {
        this.toObject()
    }

    override fun setNonNullParameter(ps: PreparedStatement, i: Int, parameter: Any?, jdbcType: JdbcType?) {
        ps.setString(i, parameter.toJsonString())
    }

    override fun getNullableResult(rs: ResultSet, columnName: String): Any? {
        return rs.getString(columnName).parse()
    }

    override fun getNullableResult(rs: ResultSet, columnIndex: Int): Any? {
        return rs.getString(columnIndex).parse()
    }

    override fun getNullableResult(cs: CallableStatement, columnIndex: Int): Any? {
        return cs.getString(columnIndex).parse()
    }
}