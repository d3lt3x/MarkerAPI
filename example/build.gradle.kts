plugins {
	`java-library`
	id("com.github.johnrengelman.shadow") version "8.1.1"
	id("xyz.jpenilla.run-paper") version "2.0.0"
	id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
}

group = "me.delta.mc.marker"
version = "1.0-SNAPSHOT"
description = "MarkerAPI"

dependencies {
	compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
	implementation(project(":api"))
}

tasks {
	bukkit {
		name = "Marker-Example"

		version = project.version.toString()

		main = "me.delta.mc.marker.MarkerExample"

		apiVersion = "1.19"

		prefix = "Marker-Example"

		authors = listOf("d3lt3x")

		description = "Area Marking/Selection API"

		website = "https://github.com/d3lt3x"

		depend = listOf("MarkerAPI")
	}
}
