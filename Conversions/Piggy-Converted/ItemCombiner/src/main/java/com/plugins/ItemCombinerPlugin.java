package com.plugins;

import com.plugins.Collections.Bank;
import com.plugins.Collections.Inventory;
import com.plugins.Collections.TileObjects;
import com.plugins.Collections.query.TileObjectQuery;
import com.plugins.EthanApiPlugin;
import com.plugins.InteractionApi.BankInteraction;
import com.plugins.InteractionApi.TileObjectInteraction;
import com.plugins.PacketUtilsPlugin;
import com.plugins.Packets.MousePackets;
import com.plugins.Packets.MovementPackets;
import com.plugins.Packets.WidgetPackets;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.plugins.PiggyUtilsPlugin;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.ObjectComposition;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.HotkeyListener;

import java.util.Arrays;

@PluginDescriptor(
        name = "<html><font color=\"#FF9DF9\">[PP]</font> Item Combiner</html>",
        description = "Automatically banks & combines items for you",
        enabledByDefault = false,
        tags = {"ethan", "piggy"}
)
@PluginDependency(EthanApiPlugin.class)
@PluginDependency(PacketUtilsPlugin.class)
@PluginDependency(PiggyUtilsPlugin.class)
@Slf4j
public class ItemCombinerPlugin extends Plugin {
    @Inject
    private Client client;
    @Inject
    private MousePackets mousePackets;
    @Inject
    private WidgetPackets widgetPackets;
    @Inject
    private EthanApiPlugin api;
    @Inject
    private ItemCombinerConfig config;
    @Inject
    private KeyManager keyManager;
    private boolean started;
    private int afkTicks;
    private boolean deposit;
    private boolean isMaking;

    @Provides
    private ItemCombinerConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(ItemCombinerConfig.class);
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
            || !started
            || EthanApiPlugin.isMoving()
            || client.getLocalPlayer().getAnimation() != -1) {
            afkTicks = 0;
            return;
        }

        if (isMaking) {
            if (isDoneMaking()) {
                isMaking = false;
            }
            return;
        }

        if (deposit) {
            if (Bank.isOpen()) {
                Widget widget = client.getWidget(WidgetInfo.BANK_DEPOSIT_INVENTORY);
                MousePackets.queueClickPacket();
                WidgetPackets.queueWidgetAction(widget, "Deposit", "Deposit inventory");
                deposit = false;
            } else {
                findBank();
            }
            return;
        }

        if (!hasItemOne()) {
            if (!Bank.isOpen()) {
                findBank();
                return;
            }
            withdrawItemOne();
            return;
        }

        if (!hasItemTwo()) {
            if (!Bank.isOpen()) {
                findBank();
                return;
            }
            withdrawItemTwo();
            return;
        }

        if (Bank.isOpen()) {
            MousePackets.queueClickPacket();
            MovementPackets.queueMovement(client.getLocalPlayer().getWorldLocation());
            return;
        }

        Widget potionWidget = client.getWidget(17694734);
        if (potionWidget != null && !potionWidget.isHidden()) {
            log.info("widget visible");
            MousePackets.queueClickPacket();
            WidgetPackets.queueResumePause(17694734, config.itemTwoAmt());
            isMaking = true;
            return;
        }

        useItems();
    }

    private boolean isDoneMaking() {
        return Inventory.getEmptySlots() == config.itemOneAmt();
    }

    private void findBank() {
        TileObjects.search()
                .filter(tileObject -> {
                    ObjectComposition objectComposition = TileObjectQuery.getObjectComposition(tileObject);
                    return objectComposition.getName().toLowerCase().contains("bank") ||
                            Arrays.stream(objectComposition.getActions()).anyMatch(action -> action != null && action.toLowerCase().contains("bank"));
                })
                .nearestToPlayer()
                .ifPresent(tileObject -> {
                    TileObjectInteraction.interact(tileObject, "Use", "Bank");
                });

        if (!deposit) {
            deposit = true;
        }
    }

    private void withdrawItemOne() {
        Bank.search()
                .withName(config.itemOneName())
                .first()
                .ifPresent(item -> BankInteraction.withdrawX(item, config.itemOneAmt()));
    }

    private void withdrawItemTwo() {
        Bank.search()
                .withName(config.itemTwoName())
                .first()
                .ifPresent(item -> BankInteraction.withdrawX(item, config.itemTwoAmt()));
    }

    private void useItems() {
        Widget itemOne = Inventory.search().filter(item -> item.getName().contains(config.itemOneName())).first().get();
        Widget itemTwo = Inventory.search().filter(item -> item.getName().contains(config.itemTwoName())).first().get();

        MousePackets.queueClickPacket();
        MousePackets.queueClickPacket();
        WidgetPackets.queueWidgetOnWidget(itemOne, itemTwo);
    }

    private boolean hasItemOne() {
        return Inventory.search().filter(item -> item.getName().contains(config.itemOneName())).first().isPresent();
    }

    private boolean hasItemTwo() {
        return Inventory.search().filter(item -> item.getName().contains(config.itemTwoName())).first().isPresent();
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
}
