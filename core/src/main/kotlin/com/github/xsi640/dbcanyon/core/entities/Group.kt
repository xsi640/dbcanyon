package com.github.xsi640.dbcanyon.core.entities

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.github.xsi640.dbcanyon.core.AuditEntity

@TableName
class Group(
    @TableId
    var id: Long,
    var name: String,
    var description: String
) : AuditEntity()