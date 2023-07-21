rootProject.name = "EV-Converted"

include(":AutoTele")
include(":CalvarionHelper")
include(":E3t4g")
include(":EthanApi")
include(":GauntletFlicker")
include(":Harpoon2Ticker")
include(":LavaRunecrafter")
include(":NightmareHelper")
include(":PrayerFlicker")
include(":PrayerFlicker")
include(":RunEnabler")
include(":Superglass")
include(":Upkeep")
include(":PathingTesting")
include(":Example")
include(":DeveloperTools")

for (project in rootProject.children) {
    project.apply {
        projectDir = file(name)
        buildFileName = "$name.gradle.kts"

        require(projectDir.isDirectory) { "Project '${project.path} must have a $projectDir directory" }
        require(buildFile.isFile) { "Project '${project.path} must have a $buildFile build script" }
    }
}
