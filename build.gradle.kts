plugins {
    java
    `maven-publish`

}

group = "com.manya"
version = "1.0.22"


repositories {
    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }

}

dependencies {
    implementation("org.jetbrains:annotations:20.1.0")
    compileOnly("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")
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

