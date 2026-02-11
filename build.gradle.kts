import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.gradle.jvm.tasks.Jar

plugins {
    kotlin("jvm") version "2.0.10"
    id("org.jetbrains.compose") version "1.8.2"
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.10"
}

group = "io.github.lingerjab"
version = "1.0.0"
repositories {
    maven { url = uri("https://maven.aliyun.com/repository/public/") }
    maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin/") }
    mavenCentral()
    google()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(compose.material3)
    implementation(compose.desktop.currentOs)
    implementation(compose.materialIconsExtended)
    testImplementation(kotlin("test"))
}


compose.desktop {
    application {
        mainClass = "io.github.lingerjab.MazeSolverWindowKt"
        nativeDistributions {
            // 支持在 CI 中按系统生成安装包：Linux(AppImage)、macOS(Dmg)、Windows(Exe/Msi)
            targetFormats(TargetFormat.AppImage, TargetFormat.Dmg, TargetFormat.Exe, TargetFormat.Msi)
            packageName = "Maze Solver"
            packageVersion = "1.0.0"
            includeAllModules = false

            windows { iconFile.set(project.file("src/main/resources/favicon.ico")) }
        }
    }
}

kotlin {
    jvmToolchain(21) // Java 21
}

// 让 `./gradlew jar` 产出的 JAR 自带可执行入口，避免出现“no main manifest attribute”
tasks.withType<Jar>().configureEach {
    manifest {
        attributes["Main-Class"] = "io.github.lingerjab.MazeSolverWindowKt"
    }
}
