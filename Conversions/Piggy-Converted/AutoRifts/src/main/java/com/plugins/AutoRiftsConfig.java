package com.plugins;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Keybind;


@ConfigGroup("AutoRifts")
public interface AutoRiftsConfig extends Config {
    @ConfigItem(
            keyName = "Toggle",
            name = "Toggle",
            description = "",
            position = 0
    )
    default Keybind toggle() {
        return Keybind.NOT_SET;
    }

    @ConfigItem(
            keyName = "cosmicRunes",
            name = "Completed lost city?",
            description = "Allow cosmic runecrafting",
            position = 1
    )
    default boolean cosmicRunes() {
        return false;
    }

    @ConfigItem(
            keyName = "lawRunes",
            name = "Completed Troll Stronghold?",
            description = "Allow law runecrafting",
            position = 2
    )
    default boolean lawRunes() {
        return false;
    }

    @ConfigItem(
            keyName = "deathRunes",
            name = "Completed Mourning's End Part II",
            description = "Allow death runecrafting",
            position = 3
    )
    default boolean deathRunes() {
        return false;
    }

    @ConfigItem(
            keyName = "bloodRunes",
            name = "Completed Sins of the Father",
            description = "Allow blood runecrafting",
            position = 4
    )
    default boolean bloodRunes() {
        return false;
    }

    @ConfigItem(
            keyName = "startFrags",
            name = "Starting Fragments",
            description = "How many fragments you should get before leaving the starting zone",
            position = 5
    )
    default int startingFrags() {
        return 60;
    }

    @ConfigItem(
            keyName = "minFrags",
            name = "Minimum Fragments",
            description = "When you should mine more fragments",
            position = 6
    )
    default int minFrags() {
        return 24;
    }

    @ConfigItem(
            keyName = "ignorePortal",
            name = "Ignore Portal Ess",
            description = "How much essence you should have to ignore portal",
            position = 7
    )
    default int ignorePortal() {
        return 20;
    }

    @ConfigItem(
            keyName = "emptySlots",
            name = "Empty Inv slots",
            description = "How many inventory slots are free (kek uims)",
            position = 8
    )
    default int emptySlots() {
        return 24;
    }

    @ConfigItem(
            keyName = "dropRunes",
            name = "Drop Runes",
            description = "Drop Runes instead of depositing (kek uim)",
            position = 9
    )
    default boolean dropRunes() {
        return false;
    }
}
