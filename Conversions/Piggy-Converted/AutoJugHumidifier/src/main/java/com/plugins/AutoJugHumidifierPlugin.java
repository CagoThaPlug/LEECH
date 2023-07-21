package com.plugins;

import com.plugins.API.SpellUtil;
import com.plugins.Collections.Bank;
import com.plugins.Collections.Inventory;
import com.plugins.Collections.TileObjects;
import com.plugins.Collections.query.TileObjectQuery;
import com.plugins.EthanApiPlugin;
import com.plugins.InteractionApi.BankInteraction;
import com.plugins.InteractionApi.BankInventoryInteraction;
import com.plugins.InteractionApi.TileObjectInteraction;
import com.plugins.PacketUtilsPlugin;
import com.plugins.Packets.MousePackets;
import com.plugins.Packets.MovementPackets;
import com.plugins.Packets.WidgetPackets;
import com.google.inject.Inject;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.ItemID;
import net.runelite.api.ObjectComposition;
import net.runelite.api.TileObject;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.HotkeyListener;

import java.util.Arrays;
import java.util.Optional;

@PluginDescriptor(
        name = "<html><font color=\"#FF9DF9\">[PP]</font> Jug Filler</html>",
        description = "Humidify Jugs",
        enabledByDefault = false,
        tags = {"ethan", "piggy"}
)
@PluginDependency(EthanApiPlugin.class)
@PluginDependency(PacketUtilsPlugin.class)
@PluginDependency(PiggyUtilsPlugin.class)
@Slf4j
public class AutoJugHumidifierPlugin extends Plugin {
    @Inject
    private Client client;
    @Inject
    private AutoJugHumidifierConfig config;
    @Inject
    private KeyManager keyManager;

    private boolean started;
    private State state;
    private int timeout;

    @Provides
    private AutoJugHumidifierConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(AutoJugHumidifierConfig.class);
    }

    @Override
    protected void startUp() throws Exception {
        keyManager.registerKeyListener(toggle);
    }

    @Override
    protected void shutDown() throws Exception {
        keyManager.unregisterKeyListener(toggle);
    }

    @Subscribe
    private void onGameTick(GameTick event) {
        if (client.getGameState() != GameState.LOGGED_IN
            || !started) {
            return;
        }

        if (timeout > 0 && state == State.WAIT) {
            timeout--;
            return;
        }

        state = getNextState();

        switch (state) {
            case BANK:
                bank();
                break;
            case HUMIDIFY:
                castHumidify();
                break;
            case WAIT:
                break;
        }
    }

    public State getNextState() {
        if (Inventory.search().withId(ItemID.JUG_OF_WATER).result().isEmpty()) {
            if (!Inventory.search().withId(ItemID.JUG).result().isEmpty()) {
                return State.HUMIDIFY;
            }
        }

        return State.BANK;
    }

    private void castHumidify() {
        Widget humidify = SpellUtil.getSpellWidget(client, "humidify");
        MousePackets.queueClickPacket();
        WidgetPackets.queueWidgetAction(humidify, "Cast");
        timeout = 5;
    }

    private void bank() {
        if (!Bank.isOpen()) {
            openBank();
            return;
        }

        handleBankItems();
    }

    private void openBank() {
        Optional<TileObject> tileObject = TileObjects.search()
                .filter(object -> {
                    ObjectComposition objectComposition = TileObjectQuery.getObjectComposition(object);
                    return objectComposition.getName().toLowerCase().contains("bank") ||
                            Arrays.stream(objectComposition.getActions()).anyMatch(action -> action != null && action.toLowerCase().contains("bank"));
                })
                .nearestToPlayer();

        if (tileObject.isEmpty()) {
            return;
        }

        TileObject bank = tileObject.get();
        TileObjectInteraction.interact(bank, "Bank", "Use", "Open");
    }

    private void handleBankItems() {
        Optional<Widget> emptyJug = Inventory.search().withId(ItemID.JUG).first();
        Optional<Widget> jugOfWater = Inventory.search().withId(ItemID.JUG_OF_WATER).first();
        Optional<Widget> bankJug = Bank.search().withId(ItemID.JUG).first();

        if (emptyJug.isEmpty() && jugOfWater.isPresent()) {
            MousePackets.queueClickPacket();
            BankInventoryInteraction.useItem(ItemID.JUG_OF_WATER, "Deposit-All", "Deposit-all");
            return;
        }

        if (emptyJug.isEmpty() && bankJug.isPresent()) {
            BankInteraction.useItem(bankJug.get(), "Withdraw-All", "Withdraw-all");
            return;
        }

        if (jugOfWater.isEmpty() && emptyJug.isPresent()) {
            MousePackets.queueClickPacket();
            MovementPackets.queueMovement(client.getLocalPlayer().getWorldLocation());
            state = State.HUMIDIFY;
        }
    }

    private final HotkeyListener toggle = new HotkeyListener(() -> config.toggle()) {
        @Override
        public void hotkeyPressed() {
            toggle();
        }
    };

    public void toggle() {
        if (client.getGameState() != GameState.LOGGED_IN) {
            return;
        }
        started = !started;
    }

    enum State {
        BANK,
        HUMIDIFY,
        WAIT
    }
}
