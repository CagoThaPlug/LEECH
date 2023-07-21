package com.plugins;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Keybind;

@ConfigGroup("PowerSkiller")
public interface PowerSkillerConfig extends Config {
    @ConfigItem(
            keyName = "toggle",
            name = "Toggle",
            description = "",
            position = -2
    )
    default Keybind toggle() {
        return Keybind.NOT_SET;
    }

    @ConfigItem(
            keyName = "searchNpc",
            name = "Search NPCs (for fishing, etc)",
            description = "For things like fishing spots",
            position = -1
    )
    default boolean searchNpc() {
        return false;
    }

    @ConfigItem(
            name = "Object",
            keyName = "objectToInteract",
            description = "Game obejct you will be interacting with",
            position = 0
    )
    default String objectToInteract() {
        return "Tree";
    }

    @ConfigItem(
            name = "Tool(s)",
            keyName = "toolsToUse",
            description = "Tools required to act with your object, can type ` axe` or ` pickaxe` to ignore the type",
            position = 1
    )
    default String toolsToUse() {
        return " axe";
    }

    @ConfigItem(
            name = "Keep Items",
            keyName = "itemToKeep",
            description = "Items you don't want dropped. Separate items by comma,no space. Good for UIM",
            position = 2
    )
    default String itemsToKeep() {
        return "coins,rune pouch,divine rune pouch,looting bag,clue scroll";
    }

    @ConfigItem(
            name = "Empty slots",
            keyName = "emptySlots",
            description = "Amount of empty slots you have to skill with, mostly a UIM feature lol",
            position = 3
    )
    default int emptySlots() {
        return 28;
    }
}
