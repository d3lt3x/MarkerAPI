plugins {
	`java-library`
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
	compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
}

java {
	toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks {
	withType<JavaCompile>() {
		options.encoding = "UTF-8"
	}

	withType<Javadoc>() {
		options.encoding = "UTF-8"
	}
}