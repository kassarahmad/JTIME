import org.gradle.language.jvm.tasks.ProcessResources

plugins {
    java
    application
    eclipse
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "it.unicam.cs.mpgc"
version = "1.0.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories { mavenCentral() }

javafx {
    version = "21.0.9"
    modules = listOf("javafx.controls", "javafx.fxml")
}

application {
    mainClass.set("it.unicam.cs.mpgc.jtime125132.app.ApplicazioneJTime")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.12.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test { useJUnitPlatform() }

tasks.named<ProcessResources>("processResources") {
    doNotTrackState("Workaround: on Windows destination dir can contain unreadable/locked content")
}