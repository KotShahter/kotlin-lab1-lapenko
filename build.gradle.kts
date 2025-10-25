plugins {
    kotlin("jvm") version "2.2.10"
    kotlin("plugin.serialization") version "2.2.0"
    id("org.jlleitschuh.gradle.ktlint") version "13.1.0"
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        version.set("1.3.0")
        outputToConsole.set(true)
        verbose.set(true)
        ignoreFailures.set(false)
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}
