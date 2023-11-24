package com.github.xsi640.dbcanyon.core

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
import org.apache.ibatis.reflection.MetaObject
import org.springframework.stereotype.Component
import java.util.Date

@Component
class AuditHandler : MetaObjectHandler {
    override fun insertFill(metaObject: MetaObject) {
        val now = Date()
        val currentUsername = ""
        super.strictInsertFill(metaObject, "creator", String::class.java, currentUsername)
        super.strictInsertFill(metaObject, "createTime", Date::class.java, now)
    }

    override fun updateFill(metaObject: MetaObject) {
        val now = Date()
        val currentUsername = ""
        super.strictUpdateFill(metaObject, "creator", String::class.java, currentUsername)
        super.strictUpdateFill(metaObject, "createTime", Date::class.java, now)
    }
}