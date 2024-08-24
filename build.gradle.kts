import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.plugin.ResolveMainClassName

val SPRING_BOOT_VERSION = "3.1.1"

plugins {
    application
    id("org.springframework.boot") version "3.1.1"
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    implementation(libs.org.springframework.boot.spring.boot.starter.data.jpa)
    implementation(libs.org.hibernate.common.hibernate.commons.annotations)
    implementation(libs.org.hibernate.validator.hibernate.validator)
    implementation(libs.com.mysql.mysql.connector.j)
    implementation(libs.jakarta.xml.bind.jakarta.xml.bind.api)
    implementation(libs.org.glassfish.jaxb.jaxb.runtime)
    implementation(libs.net.bytebuddy.byte.buddy)
    implementation(libs.org.springframework.boot.spring.boot.starter.web)
    implementation(libs.org.springframework.boot.spring.boot.starter.tomcat)
    implementation(libs.org.jboss.logging.jboss.logging)
    testImplementation(libs.org.springframework.boot.spring.boot.starter.test)
    implementation(platform("org.springframework.boot:spring-boot-dependencies:${SPRING_BOOT_VERSION}"))
}

group = "io.github.mmhelloworld"
version = "0.0.1-SNAPSHOT"
description = "idris-spring-boot-example"
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

tasks.withType<BootJar>  {
    enabled = false
}

tasks.withType<ResolveMainClassName> {
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

springBoot {
	mainClass.set("idrisspringbootexample.Main")
}

application {
    mainClass = "idrisspringbootexample.Main"
}
