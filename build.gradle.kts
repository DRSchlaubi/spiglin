import com.jfrog.bintray.gradle.BintrayExtension
import java.net.URL
import java.util.Date

plugins {
    java
    id("com.jfrog.bintray") version "1.8.4"
    `maven-publish`
    kotlin("jvm") version "1.4.10"
    id("org.jetbrains.dokka") version "1.4.10"
}

group = "com.github.johnnyjayjay"
version = "2.0.3"


repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    jcenter()
    mavenCentral()
}

dependencies {
    api(kotlin("stdlib-jdk8"))
    compileOnly("org.spigotmc", "spigot-api", "1.16.3-R0.1-SNAPSHOT")
}

val sourcesJar = tasks.register<Jar>("sourcesJar")
val javadocJar = tasks.register<Jar>("javadocJar")

kotlin {
    explicitApi()
}

tasks {
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    sourcesJar {
        dependsOn(classes)
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
    }

    javadocJar {
        archiveClassifier.set("javadoc")
        from(dokkaHtml)
    }

    dokkaHtml {
        outputDirectory.set(project.file("docs"))

        dokkaSourceSets {
            configureEach {
                includeNonPublic.set(false)
                displayName.set("JVM")
                displayName.set("JVM")

                sourceLink {
                    localDirectory.set(file("src/main/kotlin"))
                    remoteUrl.set(
                        URL(
                            "https://github.com/JohnnyJayJay/spiglin/tree/master/src/main/kotlin/"
                        )
                    )
                    remoteLineSuffix.set("#L")
                    jdkVersion.set(8)
                    externalDocumentationLink {
                        url.set(URL("https://example.com/docs/"))
                        packageListUrl.set(URL("https://gist.githubusercontent.com/DRSchlaubi/3caea5a76e11eadacdbb40b15ace46f3/raw/fe977e83824aea50acf38203ec1b56881fe38611/package-list"))
                    }
                }
            }
        }
    }
}

bintray {
    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_KEY")
    setPublications("Spiglin")
    pkg {
        version {
            name = project.version as String?
            released = Date().toString()
        }
        repo = "spiglin"
        name = "spiglin"
        vcsUrl = "https://github.com/johnnyjayjay/spiglin.git"
    }
    publish = true
}

fun org.gradle.api.publish.maven.MavenPom.applyProperties() {
    licenses {
        license {
            name.set("GNU Lesser General Public License, Version 3")
            url.set("https://www.gnu.org/licenses/lgpl-3.0.html")
        }
    }
    developers {
        developer {
            id.set("johnnyjayjay")
            email.set("johnnyjayjay02@gmail.com")
        }
    }
    scm {
        url.set("https://github.com/johnnyjayjay/spiglin")
    }
}

val javaComponent: SoftwareComponent = components["java"]

publishing {
    publications {
        create<MavenPublication>("Spiglin") {
            from(javaComponent)
            artifact(sourcesJar)
            artifact(javadocJar)
            groupId = group.toString()
            artifactId = project.name
            version = project.version.toString()
            pom.applyProperties()
            pom.withXml {
                asNode().apply {
                    appendNode("name", "spiglin")
                    appendNode(
                        "description",
                        "A collection of Kotlin extensions and utilities for the Spigot/Bukkit framework."
                    )
                    appendNode("url", "https://github.com/johnnyjayjay/spiglin/")
                }.children().last()
            }
        }
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

fun BintrayExtension.pkg(block: BintrayExtension.PackageConfig.() -> Unit) = pkg(delegateClosureOf(block))
fun BintrayExtension.PackageConfig.version(block: BintrayExtension.VersionConfig.() -> Unit) =
    version(delegateClosureOf(block))
