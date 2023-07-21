version = "1.0.0"

project.extra["PluginName"] = "Example Plugin " // This is the name that is used in the external plugin manager panel
project.extra["PluginDescription"] = "Cago's " + project.extra["PluginName"] // This is the description that is used in the external plugin manager panel
project.extra["PluginPackageId"] = "Example" // This is the plugin package folder after the default group package.
project.extra["PluginMainClassName"] = "ExamplePlugin" // This is the plugin's main class which extends Plugin
dependencies {
    implementation(project(":EthanApi"))
    implementation("com.github.weisj:darklaf:1.3.3.4")
    implementation("com.fifesoft.rtext:rtext:2.0.7")
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