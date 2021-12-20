plugins {
    java
    `maven-publish`
}

group = "com.manya"
version = "1.0"


repositories {
    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }

}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.manya"
            artifactId = "persistent-data-types"
            version = "1.0"

            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "persistent-data-types" //  optional target repository name
            url = uri("https://repo.decalium.ru/releases/")
            credentials {
                username = "root"
                password = "qDQlH6WsPoJLZUEYQln6Vb9YDwdwb3Ad3diuZIi+uyAwA8ES09xiPvH3toqzkhks"
            }
        }
    }
}

