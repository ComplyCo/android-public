pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenLocal()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }

        val githubPassword: String =
            providers.gradleProperty("gpr.key")
                .orElse(System.getenv("GITHUB_TOKEN") ?: "")
                .get()
        if (githubPassword.isNotBlank()) {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/ComplyCo/android-public")
                credentials {
                    username = "x-access-token"
                    password = githubPassword
                }
            }

        } else {
            println("#".repeat(70))
            println("settings.gradle.kts: Missing GitHub Credentials. Github packages will be skipped.")
            println("settings.gradle.kts: Recommend adding gpr.key=...PAT... to  ~/.gradle/gradle.properties.")
            println("settings.gradle.kts: See: https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry")
            println("#".repeat(70))
        }

    }
}


rootProject.name = "ComplyCoSamples"
include(":sample-compose")
include(":sample-xml")
