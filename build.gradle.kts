plugins {
    id("java")
    id("maven-publish")
}

group = "cat.breadcat"
version = "1.1.0"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("cat.breadcat:toolbox:1.3.0")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}