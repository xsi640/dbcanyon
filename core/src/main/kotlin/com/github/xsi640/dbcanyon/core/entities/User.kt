package com.github.xsi640.dbcanyon.core.entities

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.github.xsi640.dbcanyon.core.AuditEntity
import com.github.xsi640.dbcanyon.core.GroupBase

@TableName
class User(
    @TableId
    var id: Long,
    var username: String,
    var password: String,
    var mobile: String,
    var email: String,
    var description: String,
    override var groupId: Long,
) : GroupBase, AuditEntity()