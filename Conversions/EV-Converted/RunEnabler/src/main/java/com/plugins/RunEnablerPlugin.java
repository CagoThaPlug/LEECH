package com.plugins;

import com.plugins.EthanApiPlugin;
import com.plugins.Packets.MousePackets;
import com.plugins.Packets.WidgetPackets;
import net.runelite.api.events.GameTick;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;


@PluginDescriptor(name = "RunEnabler", description = "", enabledByDefault = false, tags = {"ethan"})
public class RunEnablerPlugin extends Plugin {
    @Subscribe
    public void onGameTick(GameTick e) {
        if (!EthanApiPlugin.loggedIn()) {
            return;
        }
        if (runIsOff() && hasMoreThanZeroEnergy()) {
            enableRun();
        }
    }

    boolean runIsOff() {
        return EthanApiPlugin.getClient().getVarpValue(173) == 0;
    }

    boolean hasMoreThanZeroEnergy() {
        return EthanApiPlugin.getClient().getEnergy() > 100;
    }

    void enableRun() {
        MousePackets.queueClickPacket();
        WidgetPackets.queueWidgetActionPacket(1, 10485787, -1, -1);
    }
}
