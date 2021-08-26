import net.md_5.specialsource.JarMapping
import net.md_5.specialsource.JarRemapper
import net.md_5.specialsource.provider.JarProvider
import net.md_5.specialsource.provider.JointProvider
import org.gradle.api.tasks.bundling.Jar
import net.md_5.specialsource.Jar as SpecialJar

plugins {
    id("org.jetbrains.dokka") version "1.5.0"
    `maven-publish`
    signing
}

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("net.md-5:SpecialSource:1.10.0")
    }
}

val api = project(":customentity-api")

dependencies {
    implementation(api)
}

subprojects {
    configurations {
        create("mojangMapping")
        create("spigotMapping")
    }

    repositories {
        maven("https://libraries.minecraft.net")
        mavenLocal()
    }

    dependencies {
        implementation(api)
        implementation(requireNotNull(parent)) // customentity-core
    }

    tasks {
        jar {
            doLast {
                fun remap(jarFile: File, outputFile: File, mappingFile: File, reversed: Boolean = false) {
                    val inputJar = SpecialJar.init(jarFile)

                    val mapping = JarMapping()
                    mapping.loadMappings(mappingFile.canonicalPath, reversed, false, null, null)

                    val provider = JointProvider()
                    provider.add(JarProvider(inputJar))
                    mapping.setFallbackInheritanceProvider(provider)

                    val mapper = JarRemapper(mapping)
                    mapper.remapJar(inputJar, outputFile)
                    inputJar.close()
                }

                val archiveFile = archiveFile.get().asFile
                val obfOutput = File(archiveFile.parentFile, "remapped-obf.jar")
                val spigotOutput = File(archiveFile.parentFile, "remapped-spigot.jar")

                val configurations = project.configurations
                val mojangMapping = configurations.named("mojangMapping").get().firstOrNull()
                val spigotMapping = configurations.named("spigotMapping").get().firstOrNull()

                if (mojangMapping != null && spigotMapping != null) {
                    remap(archiveFile, obfOutput, mojangMapping, true)
                    remap(obfOutput, spigotOutput, spigotMapping)

                    spigotOutput.copyTo(archiveFile, true)
                    obfOutput.delete()
                    spigotOutput.delete()
                } else {
                    throw IllegalStateException("Mojang and Spigot mapping should be specified for ${project.path}")
                }
            }
        }
    }
}

tasks {
    jar {
        archiveClassifier.set("core")
    }

    register<Jar>("paperJar") {
        from(sourceSets["main"].output)

        subprojects.forEach {
            val paperJar = it.tasks.jar.get()
            dependsOn(paperJar)
            from(zipTree(paperJar.archiveFile))
        }
    }

    register<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        (listOf(project) + subprojects).forEach { from(it.sourceSets["main"].allSource) }
    }

    register<Jar>("dokkaJar") {
        archiveClassifier.set("javadoc")
        dependsOn("dokkaHtml")

        from("$buildDir/dokka/html/") {
            include("**")
        }
    }
}

publishing {
    publications {
        register<MavenPublication>("customentity") {
            artifactId = "customentity"

            from(components["java"])
            artifact(tasks["paperJar"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["dokkaJar"])

            repositories {
                mavenLocal()

                maven {
                    name = "central"

                    credentials.runCatching {
                        val nexusUsername: String by project
                        val nexusPassword: String by project
                        username = nexusUsername
                        password = nexusPassword
                    }.onFailure {
                        logger.warn("Failed to load nexus credentials, Check the gradle.properties")
                    }

                    url = uri(
                        if ("SNAPSHOT" in version) {
                            "https://s01.oss.sonatype.org/content/repositories/snapshots/"
                        } else {
                            "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
                        }
                    )
                }
            }

            pom {
                name.set("customentity")
                description.set("pom description")
                url.set("https://github.com/xenon245/Custom-Entity")

                licenses {
                    license {
                        name.set("GNU General Public License version 3")
                        url.set("https://opensource.org/licenses/GPL-3.0")
                    }
                }

                developers {
                    developer {
                        id.set("monull")
                        name.set("Monull")
                        email.set("monull2452@gmail.com")
                        url.set("https://github.com/monull")
                        roles.addAll("developer")
                        timezone.set("Asia/Seoul")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/monull/Custom-Entity.git")
                    developerConnection.set("scm:git:ssh://github.com:monull/Custom-Entity.git")
                    url.set("https://github.com/monull/Custom-Entity")
                }
            }
        }
    }
}

signing {
    isRequired = true
    sign(tasks.jar.get(), tasks["paperJar"], tasks["sourcesJar"], tasks["dokkaJar"])
    sign(publishing.publications["customentity"])
}