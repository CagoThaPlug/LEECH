package com.plugins;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.plugins.EthanApiPlugin;
import com.plugins.PacketUtilsPlugin;
import com.plugins.Packets.MousePackets;
import com.plugins.Packets.WidgetPackets;
import com.plugins.API.InventoryUtil;
import com.plugins.API.PrayerUtil;
import com.plugins.PiggyUtilsPlugin;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Prayer;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.HotkeyListener;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@PluginDependency(PacketUtilsPlugin.class)
@PluginDependency(EthanApiPlugin.class)
@PluginDependency(PiggyUtilsPlugin.class)
@PluginDescriptor(
        name = "<html><font color=\"#FF9DF9\">[PP]</font> One Tick Switcher</html>",
        description = ";)",
        enabledByDefault = false,
        tags = {"ethan", "piggy"}
)
@Slf4j
public class OneTickSwitcherPlugin extends Plugin {

    private static final int SPEC_BAR = 38862884;

    @Inject
    private Client client;
    @Inject
    private EthanApiPlugin api;
    @Inject
    private MousePackets mousePackets;
    @Inject
    private WidgetPackets widgetPackets;
    @Inject
    private OneTickSwitcherConfig config;
    @Inject
    private KeyManager keyManager;

    @Provides
    private OneTickSwitcherConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(OneTickSwitcherConfig.class);
    }

    @Override
    protected void startUp() throws Exception {
        keyManager.registerKeyListener(toggleSpecListener);
        keyManager.registerKeyListener(toggleOneListener);
        keyManager.registerKeyListener(toggleTwoListener);
        keyManager.registerKeyListener(toggleThreeListener);
        keyManager.registerKeyListener(toggleFourListener);
        keyManager.registerKeyListener(toggleFiveListener);
        keyManager.registerKeyListener(toggleSixListener);
        keyManager.registerKeyListener(toggleSevenListener);
        keyManager.registerKeyListener(toggleEightListener);
        keyManager.registerKeyListener(toggleNineListener);
        keyManager.registerKeyListener(toggleTenListener);
    }

    @Override
    protected void shutDown() throws Exception {
        keyManager.unregisterKeyListener(toggleSpecListener);
        keyManager.unregisterKeyListener(toggleOneListener);
        keyManager.unregisterKeyListener(toggleTwoListener);
        keyManager.unregisterKeyListener(toggleThreeListener);
        keyManager.unregisterKeyListener(toggleFourListener);
        keyManager.unregisterKeyListener(toggleFiveListener);
        keyManager.unregisterKeyListener(toggleSixListener);
        keyManager.unregisterKeyListener(toggleSevenListener);
        keyManager.unregisterKeyListener(toggleEightListener);
        keyManager.unregisterKeyListener(toggleNineListener);
        keyManager.unregisterKeyListener(toggleTenListener);
    }

    private List<String> getGearNames(String gear) {
        return Arrays.stream(gear.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public static Prayer[] parsePrayers(String prayerString) {
        String[] prayerNames = prayerString.split(",");
        return Arrays.stream(prayerNames)
                .map(String::trim)
                .map(name -> name.replace(' ', '_'))
                .map(name -> Arrays.stream(Prayer.values())
                        .filter(prayer -> prayer.name().equalsIgnoreCase(name))
                        .findFirst()
                        .orElse(null))
                .filter(prayer -> prayer != null)
                .toArray(Prayer[]::new);
    }

    private void swapGear(List<String> gearNames) {
        for (String gearName : gearNames) {
            MousePackets.queueClickPacket();
            InventoryUtil.useItemNoCase(gearName, "Equip", "Wear", "Wield");
        }
    }

    private void toggleSpec() {
        MousePackets.queueClickPacket();
        WidgetPackets.queueWidgetActionPacket(1, SPEC_BAR, -1, -1);
    }

    private final HotkeyListener toggleSpecListener = new HotkeyListener(() -> config.specToggle()) {
        @Override
        public void hotkeyPressed() {
            toggleSpec();
        }
    };

    private final HotkeyListener toggleOneListener = new HotkeyListener(() -> config.oneToggle()) {
        @Override
        public void hotkeyPressed() {
            toggleGear(getGearNames(config.oneGear()));
        }
    };

    private final HotkeyListener toggleTwoListener = new HotkeyListener(() -> config.twoToggle()) {
        @Override
        public void hotkeyPressed() {
            toggleGear(getGearNames(config.twoGear()));
        }
    };

    private final HotkeyListener toggleThreeListener = new HotkeyListener(() -> config.threeToggle()) {
        @Override
        public void hotkeyPressed() {
            toggleGear(getGearNames(config.threeGear()));
        }
    };

    private final HotkeyListener toggleFourListener = new HotkeyListener(() -> config.fourToggle()) {
        @Override
        public void hotkeyPressed() {
            toggleGear(getGearNames(config.fourGear()));
        }
    };

    private final HotkeyListener toggleFiveListener = new HotkeyListener(() -> config.fiveToggle()) {
        @Override
        public void hotkeyPressed() {
            toggleGear(getGearNames(config.fiveGear()));
        }
    };

    private final HotkeyListener toggleSixListener = new HotkeyListener(() -> config.sixToggle()) {
        @Override
        public void hotkeyPressed() {
            toggleGear(getGearNames(config.sixGear()));
        }
    };

    private final HotkeyListener toggleSevenListener = new HotkeyListener(() -> config.sevenToggle()) {
        @Override
        public void hotkeyPressed() {
            toggleGear(getGearNames(config.sevenGear()));
        }
    };

    private final HotkeyListener toggleEightListener = new HotkeyListener(() -> config.onePrayerToggle()) {
        @Override
        public void hotkeyPressed() {
            PrayerUtil.toggleMultiplePrayers(parsePrayers(config.onePrayer()));
        }
    };

    private final HotkeyListener toggleNineListener = new HotkeyListener(() -> config.twoPrayerToggle()) {
        @Override
        public void hotkeyPressed() {
            PrayerUtil.toggleMultiplePrayers(parsePrayers(config.twoPrayer()));
        }
    };

    private final HotkeyListener toggleTenListener = new HotkeyListener(() -> config.threePrayerToggle()) {
        @Override
        public void hotkeyPressed() {
            PrayerUtil.toggleMultiplePrayers(parsePrayers(config.threePrayer()));
        }
    };

    public void toggleGear(List<String> gearNames) {
        if (client.getGameState() != GameState.LOGGED_IN) {
            return;
        }
        swapGear(gearNames);
    }
}
