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
    implementation("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")
    implementation("com.google.code.gson:gson:2.8.9")

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
}



tasks {
    build {
        dependsOn(publishToMavenLocal)
    }
}
