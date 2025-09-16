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
        maven { url = uri("https://jitpack.io") }

        val githubPassword: String = providers.gradleProperty("PUBLISH_PASSWORD").orElse(System.getenv("PUBLISH_PASSWORD")).get()
        if (githubPassword.isNotBlank()) {
            println("Pulling GitHub Packages with credentials: ${githubPassword.take(6)}...")
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/ComplyCo/android-public")
                credentials {
                    username = "x-access-token"
                    password = githubPassword
                }
            }
        } else println("Unable to pull GitHub Packages due to missing credentials")
    }
}

rootProject.name = "ComplySample"
include(":sample-compose")
include(":sample-xml")
