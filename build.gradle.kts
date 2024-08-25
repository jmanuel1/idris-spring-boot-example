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
    annotationProcessor("io.micronaut:micronaut-inject-java:$MICRONAUT_VERSION")
    implementation("io.micronaut:micronaut-inject")
    annotationProcessor("io.micronaut:micronaut-http-validation:$MICRONAUT_VERSION")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor:2.11.0")
    annotationProcessor(platform("io.micronaut.spring:micronaut-spring-bom:5.8.0"))
    annotationProcessor("io.micronaut.spring:micronaut-spring-annotation:5.8.0")
    annotationProcessor("io.micronaut.spring:micronaut-spring-boot-annotation:$MICRONAUT_VERSION")
    annotationProcessor("io.micronaut.spring:micronaut-spring-web-annotation:$MICRONAUT_VERSION")
    annotationProcessor("io.micronaut.validation:micronaut-validation-processor:4.7.0")
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
    // [COPYDEP] FIXME: Depending on the dependencies that are copied into here
    // later
    implementation(layout.projectDirectory.dir("build").dir("exec").dir("idrisspringbootexample_app").asFileTree)
}

group = "io.github.mmhelloworld"
version = "0.0.1-SNAPSHOT"
description = "idris-micronaut-example"
java.sourceCompatibility = JavaVersion.VERSION_17

tasks.register<Copy>("copyDependencies") {
    from(configurations.runtimeClasspath)
    val idrisBuildDir = layout.projectDirectory.dir("build/exec/idrisspringbootexample_app")
    exclude("$idrisBuildDir/**")
    eachFile(object : Action<FileCopyDetails> {
      override fun execute(f: FileCopyDetails) {
        /* println(f.file.absolutePath) */
        if (idrisBuildDir.asFileTree.contains(f.file)) {
          f.exclude()
        }
      }
    })
    into(idrisBuildDir)
    duplicatesStrategy = DuplicatesStrategy.WARN
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

task<Exec>("compileIdris") {
    commandLine("idris2", "--build", "idrisspringbootexample.ipkg")
    workingDir(layout.projectDirectory)
    environment("IDRIS2_CG", "jvm")
}

tasks.named("compileJava") {
  // See COPYDEP.
  dependsOn("compileIdris", "copyDependencies")
}

application {
    mainClass = "idrisspringbootexample.Main"
}
