dependencies {
    implementation("com.baomidou:mybatis-plus-boot-starter:3.5.4.1")
    implementation(project(":database-plugin"))
    implementation(project(":database-plugin-mysql"))

    testRuntimeOnly("mysql:mysql-connector-java:8.0.33")
}