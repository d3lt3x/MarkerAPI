plugins {
	`java-library`
	id("com.github.johnrengelman.shadow") version "8.1.1"
	id("xyz.jpenilla.run-paper") version "2.0.0"
	id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
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
	implementation(project(":api"))
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

	runServer {
		minecraftVersion("1.19.4")
	}

	bukkit {
		name = "Marker-API"

		version = project.version.toString()

		main = "me.delta.mc.marker.MarkerExample"

		apiVersion = "1.19"

		prefix = "MarkerAPI"

		authors = listOf("d3lt3x")

		description = "Area Marking/Selection API"

		website =  "https://github.com/d3lt3x"
	}
}