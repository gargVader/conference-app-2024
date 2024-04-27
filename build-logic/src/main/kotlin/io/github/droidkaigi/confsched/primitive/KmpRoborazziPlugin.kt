package io.github.droidkaigi.confsched.primitive

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency
import org.gradle.kotlin.dsl.get

@Suppress("unused")
class KmpRoborazziPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("io.github.takahirom.roborazzi")
                apply("com.google.devtools.ksp")
            }
            android {
                testOptions {
                    unitTests {
                        all {
                            it.jvmArgs("-noverify")
                            it.maxParallelForks = Runtime.getRuntime().availableProcessors()
                            // -Pscreenshot to filter screenshot tests
                            it.useJUnit {
                                if (project.hasProperty("screenshot")) {
                                    project.logger.lifecycle("Screenshot tests are included")
                                    includeCategories("io.github.droidkaigi.confsched.testing.category.ScreenshotTests")
                                }
                            }
                        }
                    }
                }
            }
            kotlin {
                sourceSets.getByName("androidUnitTest") {
                    val kspConfiguration = configurations["kspAndroid"]
                    kspConfiguration.dependencies.add(
                        libs.library("showkaseProcessor").let {
                            DefaultExternalModuleDependency(
                                it.module.group,
                                it.module.name,
                                it.versionConstraint.requiredVersion
                            )
                        }
                    )
                    dependencies {
                        implementation(libs.library("showkaseRuntime"))
                        implementation(libs.library("androidxTestEspressoEspressoCore"))
                        implementation(libs.library("junit"))
                        implementation(libs.library("robolectric"))
                        implementation(libs.library("androidxTestExtJunit"))
                        implementation(libs.library("roborazzi"))
                        implementation(libs.library("roborazziCompose"))
                    }
                }
            }
        }
    }
}