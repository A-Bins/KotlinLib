import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

group = "kr.bins"
version = "undefined"



plugins {
    id("idea")
    kotlin("jvm") version "1.8.0"
    kotlin("plugin.serialization") version "1.8.0"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}


repositories {
    mavenCentral()
    maven {
        name = "papermc-repo"
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
}

dependencies {

    shadow ("org.jetbrains.kotlin:kotlin-stdlib")
    shadow("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    shadow("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    shadow("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    compileOnly("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")

}

tasks {
    processResources {
        filesMatching("plugin.yml") {
            expand("version" to project.version)
        }
    }
    createJar("create") {

        var dest = File("C:/Users/a0103/바탕 화면/모음지이이입/버킷 모음지이입/TheOutpost/plugins")
        val pluginName = archiveFileName.get()
        val pluginFile = File(dest, pluginName)
        if (pluginFile.exists()) dest = File(dest, "update")
        doLast {
            copy {
                from(archiveFile)
                into(dest)
            }
        }
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "16"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "16"
    }
}
fun TaskContainer.createJar(name: String, configuration: ShadowJar.() -> Unit) {
    create<ShadowJar>(name) {
        archiveBaseName.set(project.name.replace("Lib", ""))
        archiveVersion.set(kotlin.coreLibrariesVersion) // For bukkit plugin update
        from(sourceSets["main"].output)
        configurations = listOf(project.configurations.shadow.get().apply { isCanBeResolved = true })
        configuration()
    }
}