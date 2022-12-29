plugins {
    java
    `maven-publish`

}

group = "com.manya"
version = "1.0.24"


repositories {
    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }

}

dependencies {
    compileOnly("org.jetbrains:annotations:23.0.0")
    testImplementation("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
        // Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
        // See https://openjdk.java.net/jeps/247 for more information.
        options.release.set(8)
        options.compilerArgs.add("-parameters")
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.manya"
            artifactId = "persistent-data-types"
            version = version

            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "persistent-data-types" //  optional target repository name
            url = uri("https://repo.decalium.ru/releases/")
            credentials {
                username = System.getenv("REPOSILITE_USER")
                password = System.getenv("REPOSILITE_TOKEN")
            }
        }
    }
}

