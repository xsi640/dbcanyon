package com.github.xsi640.dbcanyon.core.entities

import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.github.xsi640.dbcanyon.core.AuditEntity
import com.github.xsi640.dbcanyon.core.GroupBase
import com.github.xsi640.dbcanyon.plugin.DatabaseType

@TableName
class Database(
    @TableId
    var id: Long,
    var name: String,
    var type: DatabaseType,
    var url: String,
    var username: String,
    var password: String,
    var port: Int,
    @TableField(javaType = true, typeHandler = EntityTypeHandler::class)
    var parameters: Map<String, String?>,
    var description: String,
    @TableField(javaType = true, typeHandler = EntityTypeHandler::class)
    var ssh: SSHClient = SSHClient(),
    override var groupId: Long
) : AuditEntity(), GroupBase

class SSHClient(
    var enabled: Boolean = false,
    var host: String = "",
    var port: Int = 0,
    var username: String = "",
    var password: String = "",
    var privateKey: String = "",
    var passphrase: String = "",
    var timeout: Int = 30000,
)