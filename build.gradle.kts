plugins {
    `java-library`
    `maven-publish`
}

group = "me.delta.mc.marker"
version = "1.0-SNAPSHOT"
description = "MarkerAPI"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.maven.apache.org/maven2/")
}

dependencies {
    val paper = "io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT"
    val snakeyaml = "org.yaml:snakeyaml:2.0"

    compileOnly(paper){
        exclude("org.yaml", "snakeyaml")
        because("Snakeyaml 1.33 has a vulnerability.")
    }

    compileOnly(snakeyaml){
        because("We need to replace the removed vulnerable dependency")
    }

    testCompileOnly(paper){
        exclude("org.yaml", "snakeyaml")
    }
    testCompileOnly(snakeyaml)

}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}
