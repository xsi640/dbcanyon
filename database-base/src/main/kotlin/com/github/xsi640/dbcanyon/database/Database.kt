package com.github.xsi640.dbcanyon.database

interface Database {
    var name: String
    var schemas: List<Schema>
    var comment: String
}

interface Schema {
    var databaseName: String
    var name: String
    var comment: String
}

interface Table {
    var name: String
    var comment: String
    var columnList: MutableList<TableColumn>
}

interface TableColumn {
    var name: String
    var type: String
    var dataType: Int
    var defaultValue: String
    var autoIncrement: Boolean
    var comment: String
    var primaryKey: Boolean
    var size: Int
    var decimalDigits: Int
    var numPrecRadix: Int
    var charOctetLength: Int
    var ordinalPosition: Int
    var nullable: Boolean
    var generatedColumn: Boolean
}