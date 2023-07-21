package com.plugins;

import com.plugins.enums.ExampleEnum;
import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup("Example")
public interface ExampleConfig extends Config {

    @ConfigSection(
            name = "Example",
            description = "Example",
            position = 0
    )
    String Main = "Main";

    @ConfigItem(
            keyName = "ExampleString",
            name = "ExampleString",
            description = "ExampleString",
            position = 1,
            section = "Main"
    )
    default String Example() { return "String"; }

    @ConfigItem(
            keyName = "ExampleBoolean",
            name = "ExampleBoolean",
            description = "ExampleBoolean",
            position = 2,
            section = "Main"
    )
    default boolean ExampleBoolean() { return false; }

    @ConfigItem(
            keyName = "ExampleInt",
            name = "ExampleInt",
            description = "ExampleInt",
            position = 3,
            section = "Main"
    )
    default int ExampleInt() { return 0; }

    @ConfigItem(
            keyName = "ExampleHotKey",
            name = "ExampleHotKey",
            description = "ExampleHotKey",
            position = 4,
            section = "Main"
    )
    default Keybind ExampleHotKey() { return Keybind.NOT_SET; }

    @ConfigItem(
            keyName = "ExampleColor",
            name = "ExampleColor",
            description = "ExampleColor",
            position = 5,
            section = "Main"
    )
    default Color ExampleColor() { return Color.WHITE; }

    @ConfigItem(
            keyName = "ExampleEnum",
            name = "ExampleEnum",
            description = "ExampleEnum",
            position = 6,
            section = "Main"
    )
    default ExampleEnum ExampleEnum() { return ExampleEnum.E; }


}
