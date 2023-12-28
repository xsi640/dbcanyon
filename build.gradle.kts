import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.noarg")
    kotlin("plugin.allopen")
}

allprojects {

    apply {
        plugin("idea")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("kotlin")
        plugin("kotlin-spring")
        plugin("kotlin-allopen")
        plugin("kotlin-noarg")
    }

    group = "com.github.xsi640"
    version = "1.0.0"
    java.sourceCompatibility = JavaVersion.VERSION_17
    java.targetCompatibility = JavaVersion.VERSION_17


    val vers = mapOf(
        "commons_io" to "2.11.0",
        "commons_codec" to "1.15",
        "commons_lang" to "3.12.0",
    )

    rootProject.extra.set("vers", vers)
    rootProject.extra.set("docker_registry", "192.168.1.254:5000")
    rootProject.extra.set("docker_host", "192.168.1.254")

    dependencies {
        implementation("commons-io:commons-io:${vers["commons_io"]}")
        implementation("commons-codec:commons-codec:${vers["commons_codec"]}")
        implementation("org.apache.commons:commons-lang3:${vers["commons_lang"]}")

        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        implementation("org.jetbrains.kotlin:kotlin-allopen")
        implementation("org.jetbrains.kotlin:kotlin-noarg")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("junit:junit")

    }

    repositories {
        mavenLocal()
        maven { url = uri("https://maven.aliyun.com/repository/central") }
        maven { url = uri("https://maven.aliyun.com/repository/public") }
        maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin") }
        maven { url = uri("https://maven.aliyun.com/repository/apache-snapshots") }
        mavenCentral()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    val jar: Jar by tasks
    val bootJar: BootJar by tasks
    jar.enabled = true
    bootJar.enabled = false
}

//apply(from = rootProject.file("buildscript/root.gradle.kts"))