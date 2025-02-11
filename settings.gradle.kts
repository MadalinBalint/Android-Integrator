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
        mavenCentral()
    }
}

rootProject.name = "Android Integrator"
include(":app")

include(":analytics-component")
include(":remoteconfig-component")
include(":designsystem")
include(":home-ui")
include(":routes")
include(":rickandmorty-component")
include(":rickandmorty-ui")
include(":tmdb-component")
include(":tmdb-ui")
include(":viewmodel")
include(":shared")
include(":exchangerate-component")
include(":exchangerate-ui")
include(":binance-component")
include(":binance-ui")
include(":testing")
