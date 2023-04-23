plugins {
    `java-library`
    `maven-publish`
}

group = "me.delta.mc.marker"
version = "1.0-SNAPSHOT"
description = "MarkerAPI"

project.allprojects {
    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://oss.sonatype.org/content/groups/public/")
        maven("https://repo.maven.apache.org/maven2/")
    }

    tasks {
        withType<JavaCompile> {
            options.encoding = "UTF-8"
        }

        withType<Javadoc> {
            options.encoding = "UTF-8"
        }
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

