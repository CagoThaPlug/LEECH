package com.plugins;

import com.google.inject.Provides;
import com.plugins.Packets.MovementPackets;
import net.runelite.api.ChatMessageType;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.util.HotkeyListener;

import javax.inject.Inject;
import java.io.IOException;
import java.time.Duration;

@PluginDependency(EthanApiPlugin.class)
@PluginDependency(PacketUtilsPlugin.class)
public class ExamplePlugin extends Plugin {

    @Inject
    KeyManager keyManager;

    @Inject
    private ExampleConfig config;

    private final HotkeyListener startButton = new HotkeyListener(() -> config.ExampleHotKey()) {
        @Override
        public void hotkeyPressed() {
            EthanApiPlugin.sendClientMessage("Start button pressed!");
        }
    };

    @Provides
    ExampleConfig getConfig(final ConfigManager configManager) {
        return configManager.getConfig(ExampleConfig.class);
    }

    @Override
    public void startUp() {
        EthanApiPlugin.sendClientMessage("Hello " + EthanApiPlugin.client.getLocalPlayer().getName() + "!");
    }

    @Override
    public void shutDown() {
        EthanApiPlugin.sendClientMessage("Goodbye " + EthanApiPlugin.client.getLocalPlayer().getName() + "!");
    }

    @Subscribe
    public void onChatMessage(ChatMessage event) {
        if (event.getType() == ChatMessageType.GAMEMESSAGE) {
            String message = event.getMessage();
            if (message.contains("test")) {
                int x = EthanApiPlugin.client.getLocalPlayer().getWorldLocation().getX();
                int y = EthanApiPlugin.client.getLocalPlayer().getWorldLocation().getY();
                MovementPackets.queueMovement(x + 1, y + 1, false);
                EthanApiPlugin.sendClientMessage("Test walking successful!");
            }
        }
    }
}
