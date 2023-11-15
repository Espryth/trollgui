plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") version "1.5.3"
    id("com.github.johnrengelman.shadow") version("8.1.1")
}

group = "me.espryth"
version = "1.0.0"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
}

dependencies {
    paperweight.paperDevBundle("1.19.4-R0.1-SNAPSHOT")
    implementation("org.jetbrains:annotations:20.1.0")
    api("org.incendo.interfaces:interfaces-paper:1.0.0-SNAPSHOT")
    api("org.mongodb:mongodb-driver-sync:4.11.0")
}
