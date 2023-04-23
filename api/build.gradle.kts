plugins {
    `java-library`
}

group = "me.delta.mc.marker"
version = "1.0-SNAPSHOT"
description = "MarkerAPI"

dependencies {
    val paper = "io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT"
    val snakeyaml = "org.yaml:snakeyaml:2.0"

    compileOnly(paper) {
        exclude("org.yaml", "snakeyaml")
        because("Snakeyaml 1.33 has a vulnerability.")
    }

    compileOnly(snakeyaml) {
        because("We need to replace the removed vulnerable dependency")
    }

    testCompileOnly(paper) {
        exclude("org.yaml", "snakeyaml")
    }
    testCompileOnly(snakeyaml)
}
