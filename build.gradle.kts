val MICRONAUT_VERSION = "3.10.4"

plugins {
    /* id("com.github.johnrengelman.shadow") version "7.1.2" */
    id("io.micronaut.application") version "3.7.10"
}

graalvmNative.toolchainDetection.set(false)
micronaut {
  version = MICRONAUT_VERSION
    runtime("netty")
    testRuntime("junit5")
    /* processing {
        incremental(true)
        annotations("com.example.*")
    } */
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

val idrisBuildDir = layout.projectDirectory.dir("build").dir("exec").dir("idrisspringbootexample_app")

dependencies {
  annotationProcessor("io.micronaut:micronaut-http-validation")
  implementation("io.micronaut:micronaut-http-client")
  implementation("io.micronaut:micronaut-jackson-databind")
  implementation("jakarta.annotation:jakarta.annotation-api")
  runtimeOnly("ch.qos.logback:logback-classic")
  implementation("io.micronaut.sql:micronaut-hibernate-jpa")
  implementation("io.micronaut.data:micronaut-data-tx-hibernate")
  implementation("io.micronaut.sql:micronaut-jdbc-hikari")
  runtimeOnly("com.h2database:h2")
  /* annotationProcessor("io.micronaut.validation:micronaut-validation-processor") */
  /* implementation("io.micronaut.validation:micronaut-validation") */
  /* annotationProcessor("io.micronaut:micronaut-validation-processor:$MICRONAUT_VERSION") */
  implementation("io.micronaut:micronaut-validation")
    // [COPYDEP] FIXME: Depending on the dependencies that are copied into here
    // later
    implementation(files(idrisBuildDir, idrisBuildDir.asFileTree))
    /* implementation(idrisBuildDir.asFileTree) */
}

group = "io.github.mmhelloworld"
version = "0.0.1-SNAPSHOT"
description = "idris-micronaut-example"
java {
    sourceCompatibility = JavaVersion.toVersion("17")
    targetCompatibility = JavaVersion.toVersion("17")
}

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
    output.resourcesDir = idrisBuildDir.asFile
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

tasks.named<JavaCompile>("compileJava") {
  // See COPYDEP.
  dependsOn("compileIdris", "copyDependencies")
  /* options.compilerArgs.add("-Xdiags:verbose") */
  doFirst {
    /* classpath += files(idrisBuildDir)
    classpath += idrisBuildDir.asFileTree */
    /* println(classpath.files.toString()) */

  }
}

application {
    mainClass = "idrisspringbootexample.Main"
}
