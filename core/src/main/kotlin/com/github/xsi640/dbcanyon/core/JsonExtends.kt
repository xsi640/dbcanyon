package com.github.xsi640.dbcanyon.core

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.DataInput
import java.io.File
import java.io.InputStream
import java.net.URL

object JsonExtends {
    val mapper = jacksonObjectMapper()

    init {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false)
        mapper.registerModule(
            KotlinModule.Builder()
                .configure(KotlinFeature.NullIsSameAsDefault, true)
                .build()
        )
    }

    inline fun <reified T> String.toObject(): T = mapper.readValue(this, T::class.java)
    inline fun <reified T> JsonNode.toObject(): T = mapper.convertValue(this, T::class.java)
    inline fun <reified T> File.toObject(): T = mapper.readValue(this, T::class.java)
    inline fun <reified T> InputStream.toObject(): T = mapper.readValue(this, T::class.java)
    inline fun <reified T> DataInput.toObject(): T = mapper.readValue(this, T::class.java)
    inline fun <reified T> URL.toObject(): T = mapper.readValue(this, T::class.java)
    inline fun <reified T> ByteArray.toObject(): T = mapper.readValue(this, T::class.java)
    inline fun <reified T> ByteArray.toObject(offset: Int, len: Int): T =
        mapper.readValue(this, offset, len, T::class.java)

    fun Any?.toJsonString(): String = mapper.writeValueAsString(this)
}