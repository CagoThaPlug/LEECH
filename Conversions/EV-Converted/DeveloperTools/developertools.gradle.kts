version = "1.0.0"

project.extra["PluginName"] = "Developer Tools" // This is the name that is used in the external plugin manager panel
project.extra["PluginDescription"] = "Revamped Developer Tools - Cago" // This is the description that is used in the external plugin manager panel
project.extra["PluginPackageId"] = "DeveloperTools" // This is the plugin package folder after the default group package.
project.extra["PluginMainClassName"] = "DevToolsPlugin" // This is the plugin's main class which extends Plugin
dependencies {
    implementation(project(":EthanApi"))
}
tasks {
    jar {
        manifest {
            attributes(mapOf(
                "Plugin-Version" to project.version,
                "Plugin-Id" to nameToId(project.extra["PluginName"] as String),
                "Plugin-Provider" to project.extra["PluginProvider"],
                "Plugin-Description" to project.extra["PluginDescription"],
                "Plugin-License" to project.extra["PluginLicense"]
            ))
        }
    }
}