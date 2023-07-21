rootProject.name = "Piggy-Converted"

include(":AoeWarn")
include(":AutoJugHumidifier")
include(":AutoRifts")
include(":CannonReloader")
include(":HerbCleaner")
include(":ItemCombiner")
include(":JadAutoPrayers")
include(":OneTickSwitcher")
include(":PiggyUtils")
include(":PowerSkiller")
include(":PrayAgainstPlayer")
include(":RooftopAgility")
include(":SixHourLog")
include(":SpeedDartMaker")
include(":EthanApi")

for (project in rootProject.children) {
    project.apply {
        projectDir = file(name)
        buildFileName = "$name.gradle.kts"

        require(projectDir.isDirectory) { "Project '${project.path} must have a $projectDir directory" }
        require(buildFile.isFile) { "Project '${project.path} must have a $buildFile build script" }
    }
}
include("RooftopAgility:src:main:Piggy-Converted")
findProject(":RooftopAgility:src:main:Piggy-Converted")?.name = "Piggy-Converted"
