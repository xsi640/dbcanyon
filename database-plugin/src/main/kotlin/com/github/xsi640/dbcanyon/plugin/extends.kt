package com.github.xsi640.dbcanyon.plugin

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory

fun ClassLoader.drivers(name: String): List<DatabaseDriver> {
    val mapper = ObjectMapper(YAMLFactory())
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    return mapper.readValue(
        this.getResourceAsStream(name),
        object : TypeReference<List<DefaultDatabaseDriver>>() {}
    )
}