package com.plugins;

import com.google.inject.Provides;
import com.plugins.API.InventoryUtil;
import com.plugins.API.ObjectUtil;
import com.plugins.InteractionApi.TileObjectInteraction;
import com.plugins.Packets.MousePackets;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.TileObject;
import net.runelite.api.VarPlayer;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.widgets.Widget;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@PluginDescriptor(
        name = "<html><font color=\"#FF9DF9\">[PP]</font> Cannon Reloaderr</html>",
        description = "Automatically reloads your cannon when out of cannonballs",
        enabledByDefault = false,
        tags = {"ethan", "piggy"}
)
@PluginDependency(EthanApiPlugin.class)
@PluginDependency(PacketUtilsPlugin.class)
@PluginDependency(PiggyUtilsPlugin.class)
@Slf4j
public class CannonReloaderPlugin extends Plugin {

    @Inject
    private ItemManager itemManager;
    @Inject
    private Client client;
    @Inject
    private ClientThread clientThread;
    @Inject
    private CannonReloaderConfig config;

    @Provides
    private CannonReloaderConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(CannonReloaderConfig.class);
    }

    private int cballsLeft;
    private int nextReload;
    private Random random;

    @Override
    protected void startUp() throws Exception {
        clientThread.invoke(() -> cballsLeft = client.getVarpValue(VarPlayer.CANNON_AMMO));
        random = new Random();
        nextReload = ThreadLocalRandom.current().nextInt(config.reloadMin(), config.reloadMax());
    }

    @Override
    protected void shutDown() throws Exception {
        cballsLeft = 0;
    }

    @Subscribe
    public void onVarbitChanged(VarbitChanged varbitChanged)
    {
        if (varbitChanged.getVarpId() == VarPlayer.CANNON_AMMO)
        {
            cballsLeft = varbitChanged.getValue();
        }
    }

    @Subscribe
    private void onGameTick(GameTick event) {
        if (client.getGameState() != GameState.LOGGED_IN) {
            return;
        }

        if (nextReload >= cballsLeft) {
            reloadCannon();
        }
    }

    private void reloadCannon() {
        if (EthanApiPlugin.isMoving()) {
            return;
        }

        Optional<Widget> cannonball = InventoryUtil.nameContainsNoCase("cannonball").first();

        if (cannonball.isPresent()) {
            Optional<TileObject> to = ObjectUtil.nameContainsNoCase("dwarf multicannon").nearestToPlayer();
            if (to.isPresent()) {
                MousePackets.queueClickPacket();
                TileObjectInteraction.interact(to.get(), "Fire", "fire");
                nextReload = ThreadLocalRandom.current().nextInt(config.reloadMin(), config.reloadMax() + 1);
            }
        }
    }
}
