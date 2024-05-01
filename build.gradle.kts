plugins {
    alias(libs.plugins.dependencyanalysis)
}
buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
    dependencies {
        classpath(libs.android.gradle.plugin)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.aboutlibraries.plugin)
        classpath(libs.kotlin.atomicfu)
        classpath(libs.moko.resources.generator)
    }
}

allprojects {
    apply(plugin = "kotlinx-atomicfu")
    repositories {
        google()
        mavenCentral()
    }

    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.module.group == "org.jetbrains.compose.runtime") {
                if (requested.module.name == "runtime") {
//                    println("Requested version: ${requested.version}")
                    useVersion("1.6.10-beta03")
                }
                //val version = findDefaultVersionInCatalog(requested.group, requested.name)
                //because(version.because)
            }
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        freeCompilerArgs = listOf(
            "-Xexpect-actual-classes", // Ignore expect/actual experimental state
        )
    }
}
