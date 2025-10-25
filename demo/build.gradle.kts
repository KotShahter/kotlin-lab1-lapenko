plugins {
    kotlin("jvm") version "2.2.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
    testImplementation(kotlin("test"))
    implementation(project(":logParserLibrary")) //Implements my jar library
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(24)
}