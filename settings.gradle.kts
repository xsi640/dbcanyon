rootProject.name = "dbcanyon"

pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val springBootManagementVersion: String by settings

    plugins {
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springBootManagementVersion

        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
        kotlin("plugin.noarg") version kotlinVersion
        kotlin("plugin.allopen") version kotlinVersion
    }

    repositories {
        mavenLocal()
        maven { url = uri("https://maven.aliyun.com/repository/central") }
        maven { url = uri("https://maven.aliyun.com/repository/public") }
        maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin") }
        maven { url = uri("https://maven.aliyun.com/repository/apache-snapshots") }
        mavenCentral()
    }
}

fun defineSubProject(name: String, path: String) {
    include(name)
    project(":$name").projectDir = file(path)
}

defineSubProject("app", "app")
defineSubProject("core", "core")
defineSubProject("database-base", "database-base")
defineSubProject("database-plugin", "database-plugin")
defineSubProject("database-plugin-mysql", "plugin/mysql")