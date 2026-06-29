plugins {
    id("java")
    id("maven-publish")
}

group = "cat.breadcat"
version = "1.2.1"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("cat.breadcat:toolbox:1.4.2")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}