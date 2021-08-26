plugins {
    id("org.jetbrains.dokka") version "1.5.0"
    `maven-publish`
    signing
}

tasks {
    create<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    create<Jar>("dokkaJar") {
        archiveClassifier.set("javadoc")
        dependsOn("dokkaHtml")

        from("$buildDir/dokka/html")
        include("**")
    }
}

publishing {
    publications {
        create<MavenPublication>("customentity-api") {
            artifactId = "customentity-api"
            from(components["java"])
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
                name.set("customentity-api")
                description.set("Custom Entity Plugin for paper server")
                url.set("https://github.com/xenon245/Custom-Entity")

                licenses {
                    license {
                        name.set("GNU General Public License version 3")
                        url.set("https://opensource.org/licenses/GPL-3.0")
                    }
                }

                developers {
                    developer {
                        id.set("xenon245")
                        name.set("Xenon")
                        email.set("xenon2452@gmail.com")
                        url.set("https://github.com/xenon245")
                        roles.addAll("developer")
                        timezone.set("Asia/Seoul")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/xenon245/Custom-Entity.git")
                    developerConnection.set("scm:git:ssh://github.com:xenon245/Custom-Entity.git")
                    url.set("https://github.com/xenon245/Custom-Entity")
                }
            }
        }
    }
}

signing {
    isRequired = true
    sign(tasks.jar.get(), tasks["sourcesJar"], tasks["dokkaJar"])
    sign(publishing.publications["customentity-api"])
}