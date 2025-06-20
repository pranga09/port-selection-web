plugins {
    kotlin("multiplatform") version "1.9.0"
    id("org.jetbrains.compose") version "1.5.0"
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    js(IR) {
        browser {
            binaries.executable()
        }
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(compose.web.core)
                implementation(compose.runtime)
                implementation(compose.foundation)
            }
        }
    }
}

compose.experimental {
    web.application {}
}