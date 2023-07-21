package com.plugins;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("JadAutoPrayers")
public interface JadAutoPrayersConfig extends Config {
    @ConfigItem(
            keyName = "eagleEye",
            name = "Use Eagle Eye?",
            description = "",
            position = 1
    )
    default boolean eagleEye() {
        return false;
    }

    @ConfigItem(
            keyName = "useRigour",
            name = "Use Rigour?",
            description = "",
            position = 2
    )
    default boolean rigour() {
        return false;
    }
}
