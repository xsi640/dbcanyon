package com.github.xsi640.dbcanyon.core

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import java.util.Date

abstract class AuditEntity(
    @TableField(fill = FieldFill.INSERT)
    var creator: String = "",
    @TableField(fill = FieldFill.INSERT)
    var createTime: Date = Date(),
    @TableField(fill = FieldFill.UPDATE)
    var lastModifier: String? = null,
    @TableField(fill = FieldFill.UPDATE)
    var lastModifiedDate: Date? = null
)