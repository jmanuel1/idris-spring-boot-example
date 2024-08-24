val MICRONAUT_VERSION = "3.10.4"

plugins {
    application
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    implementation(platform("io.micronaut:micronaut-bom:$MICRONAUT_VERSION"))
    annotationProcessor("io.micronaut:micronaut-http-validation")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    annotationProcessor("io.micronaut.spring:micronaut-spring-annotation")
    annotationProcessor("io.micronaut.spring:micronaut-spring-boot-annotation")
    annotationProcessor("io.micronaut.spring:micronaut-spring-web-annotation")
    annotationProcessor("io.micronaut.validation:micronaut-validation-processor")
    implementation("io.micronaut:micronaut-http-server")
    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut:micronaut-validation:$MICRONAUT_VERSION")
    implementation("jakarta.validation:jakarta.validation-api")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("io.micronaut:micronaut-http-client")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("io.micronaut.spring:micronaut-spring-boot")
    runtimeOnly("io.micronaut.spring:micronaut-spring-web")
    runtimeOnly("org.yaml:snakeyaml")
    testAnnotationProcessor("io.micronaut.spring:micronaut-spring-boot-annotation")
    testAnnotationProcessor("io.micronaut.spring:micronaut-spring-web-annotation")
    testImplementation("io.micronaut:micronaut-http-client")
    implementation(libs.org.springframework.boot.spring.boot.starter.data.jpa)
    implementation(libs.org.hibernate.common.hibernate.commons.annotations)
    implementation(libs.org.hibernate.validator.hibernate.validator)
    implementation(libs.com.mysql.mysql.connector.j)
    implementation(libs.jakarta.xml.bind.jakarta.xml.bind.api)
    implementation(libs.org.glassfish.jaxb.jaxb.runtime)
    implementation(libs.net.bytebuddy.byte.buddy)
}

group = "io.github.mmhelloworld"
version = "0.0.1-SNAPSHOT"
description = "idris-micronaut-example"
java.sourceCompatibility = JavaVersion.VERSION_17

tasks.register<Copy>("copyDependencies") {
    from(configurations.runtimeClasspath)
    into(layout.projectDirectory.dir("build/exec/idrisspringbootexample_app"))
}

sourceSets {
  main {
    output.resourcesDir = layout.projectDirectory.dir("build").dir("exec").dir("idrisspringbootexample_app").asFile
  }
}

tasks.withType<Jar>  {
    enabled = false
}

tasks.withType<CreateStartScripts> {
  enabled = false
}

tasks.named("distTar") {
  enabled = false
}

tasks.named("distZip") {
  enabled = false
}

task<Exec>("idrisCompile") {
    commandLine("idris2", "--build", "idrisspringbootexample.ipkg")
    workingDir(layout.projectDirectory)
    environment("IDRIS2_CG", "jvm")
}

tasks.named("build") {
  dependsOn("copyDependencies", "idrisCompile")
}

application {
    mainClass = "idrisspringbootexample.Main"
}
