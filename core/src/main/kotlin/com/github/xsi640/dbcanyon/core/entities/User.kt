package com.github.xsi640.dbcanyon.core.entities

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.github.xsi640.dbcanyon.core.AuditEntity

@TableName
class User(
    @TableId
    var id: Long,
    var username: String,
    var password: String,
    var mobile: String,
    var email: String,
    var description: String
) : AuditEntity()