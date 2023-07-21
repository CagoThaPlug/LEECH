package com.plugins;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Keybind;

@ConfigGroup("OneTickSwitcher")
public interface OneTickSwitcherConfig extends Config {

    @ConfigItem(keyName = "specToggle", name = "Spec", description = "Toggle to click spec", position = -1)
    default Keybind specToggle() {
        return Keybind.NOT_SET;
    }

    @ConfigSection(name = "Gear", description = "Gear two switch", position = 0)
    String gearSection = "Gear";

    @ConfigItem(keyName = "oneToggle", name = "One Toggle", description = "", position = 1, section = gearSection)
    default Keybind oneToggle() {
        return Keybind.NOT_SET;
    }

    @ConfigItem(keyName = "oneGear", name = "One Gear", description = "", position = 2, section = gearSection)
    default String oneGear() {
        return "";
    }

    @ConfigItem(keyName = "twoToggle", name = "Two Toggle", description = "", position = 3, section = gearSection)
    default Keybind twoToggle() {
        return Keybind.NOT_SET;
    }

    @ConfigItem(keyName = "twoGear", name = "Two Gear", description = "", position = 4, section = gearSection)
    default String twoGear() {
        return "";
    }

    @ConfigItem(keyName = "threeToggle", name = "Three Toggle", description = "", position = 5, section = gearSection)
    default Keybind threeToggle() {
        return Keybind.NOT_SET;
    }

    @ConfigItem(keyName = "threeGear", name = "Three Gear", description = "", position = 6, section = gearSection)
    default String threeGear() {
        return "";
    }

    @ConfigItem(keyName = "fourToggle", name = "Four Toggle", description = "", position = 7, section = gearSection)
    default Keybind fourToggle() {
        return Keybind.NOT_SET;
    }

    @ConfigItem(keyName = "fourGear", name = "Four Gear", description = "", position = 8, section = gearSection)
    default String fourGear() {
        return "";
    }

    @ConfigItem(keyName = "fiveToggle", name = "Five Toggle", description = "", position = 9, section = gearSection)
    default Keybind fiveToggle() {
        return Keybind.NOT_SET;
    }

    @ConfigItem(keyName = "fiveGear", name = "Five Gear", description = "", position = 10, section = gearSection)
    default String fiveGear() {
        return "";
    }

    @ConfigItem(keyName = "sixToggle", name = "Six Toggle", description = "", position = 11, section = gearSection)
    default Keybind sixToggle() {
        return Keybind.NOT_SET;
    }

    @ConfigItem(keyName = "sixGear", name = "Six Gear", description = "", position = 12, section = gearSection)
    default String sixGear() {
        return "";
    }

    @ConfigItem(keyName = "sevenToggle", name = "Seven Toggle", description = "", position = 13, section = gearSection)
    default Keybind sevenToggle() {
        return Keybind.NOT_SET;
    }

    @ConfigItem(keyName = "sevenGear", name = "Seven Gear", description = "", position = 14, section = gearSection)
    default String sevenGear() {
        return "";
    }

    @ConfigSection(
            name = "Prayer One",
            description = "Prayers to switch",
            position = 15
    )
    String prayerSection = "Prayer";

    @ConfigItem(keyName = "eightToggle", name = "Prayers One Toggle", description = "", position = 16, section = prayerSection)
    default Keybind onePrayerToggle() {
        return Keybind.NOT_SET;
    }

    @ConfigItem(keyName = "onePrayerFirst", name = "Prayers One", description = "", position = 17, section = prayerSection)
    default String onePrayer() {
        return "";
    }

    @ConfigItem(keyName = "nineToggle", name = "Prayers Two Toggle", description = "", position = 18, section = prayerSection)
    default Keybind twoPrayerToggle() {
        return Keybind.NOT_SET;
    }

    @ConfigItem(keyName = "twoPrayer", name = "Prayers Two", description = "", position = 19, section = prayerSection)
    default String twoPrayer() {
        return "";
    }

    @ConfigItem(keyName = "threePrayerToggle", name = "Prayers Three Toggle", description = "", position = 20, section = prayerSection)
    default Keybind threePrayerToggle() {
        return Keybind.NOT_SET;
    }

    @ConfigItem(keyName = "threePrayer", name = "Prayers Three", description = "", position = 21, section = prayerSection)
    default String threePrayer() {
        return "";
    }
}
