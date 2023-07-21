package com.plugins;

import com.plugins.Collections.Inventory;
import com.plugins.Collections.NPCs;
import com.plugins.Collections.TileObjects;
import com.plugins.Collections.query.TileObjectQuery;
import com.plugins.EthanApiPlugin;
import com.plugins.InteractionApi.InventoryInteraction;
import com.plugins.InteractionApi.NPCInteraction;
import com.plugins.InteractionApi.TileObjectInteraction;
import com.plugins.PacketUtilsPlugin;
import com.google.inject.Provides;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.NPCComposition;
import net.runelite.api.ObjectComposition;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.HotkeyListener;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;


@PluginDependency(EthanApiPlugin.class)
@PluginDependency(PacketUtilsPlugin.class)
@PluginDescriptor(
        name = "<html><font color=\"#FF9DF9\">[PP]</font> Power Skiller</html>",
        description = "Will interact with an object and drop all items when inventory is full",
        tags = {"ethan", "piggy", "skilling"}
)
public class PowerSkillerPlugin extends Plugin {

    @Inject
    private Client client;
    @Inject
    private PowerSkillerConfig config;
    @Inject
    private KeyManager keyManager;
    private State state;
    private boolean started;

    @Override
    protected void startUp() throws Exception {
        keyManager.registerKeyListener(toggle);
    }

    @Override
    protected void shutDown() throws Exception {
        keyManager.unregisterKeyListener(toggle);
    }

    @Provides
    private PowerSkillerConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(PowerSkillerConfig.class);
    }

    @Subscribe
    private void onGameTick(GameTick event) {
        if (!EthanApiPlugin.loggedIn() || !started) {
            // We do an early return if the user isn't logged in
            return;
        }

        state = getNextState();
        handleState();
    }

    private void handleState() {
        switch (state) {
            case FIND_OBJECT:
                if (config.searchNpc()) {
                    findNpc();
                } else {
                    findObject();
                }
                break;
            case DROP_ITEMS:
                dropItems();
                break;
        }
    }

    private State getNextState() {
        // self-explanatory, we just return a State if the conditions are met.
        if (EthanApiPlugin.isMoving() || client.getLocalPlayer().getAnimation() != -1) {
            // this is to prevent clicks while animating/moving.
            return State.ANIMATING;
        }

        if (!hasTools()) {
            // if the user doesn't have tools we don't want it to do anything at all lol, maybe stop the plugin if you want.
            return State.MISSING_TOOLS;
        }

        if (isDroppingItems() && !isInventoryReset()) {
            // if the user should be dropping items, we'll check if they're done
            // should sit at this state til it's finished.
            return State.DROP_ITEMS;
        }

        if (Inventory.full()) {
            // if the inventory gets full we'll start dropping items.
            // this is how it's initiated.
            return State.DROP_ITEMS;
        }

        // default it'll look for an object.
        return State.FIND_OBJECT;
    }

    private void findObject() {
        String objectName = config.objectToInteract();

        TileObjects.search().withName(objectName).nearestToPlayer().ifPresent(tileObject -> {
            ObjectComposition comp = TileObjectQuery.getObjectComposition(tileObject);
            TileObjectInteraction.interact(tileObject, comp.getActions()[0]); // find the object we're looking for.  this specific example will only work if the first Action the object has is the one that interacts with it.
            // don't *always* do this, you can manually type the possible actions. eg. "Mine", "Chop", "Cook", "Climb".
        });
    }

    private void findNpc() {
        String npcName = config.objectToInteract();

        NPCs.search().withName(npcName).nearestToPlayer().ifPresent(npc -> {
            NPCComposition comp = client.getNpcDefinition(npc.getId());
            NPCInteraction.interact(npc,comp.getActions()[0]); // For fishing spots ?
        });
    }

    private void dropItems() {
        List<Widget> itemsToDrop = Inventory.search()
                .filter(item -> !shouldKeep(item.getName()) && !isTool(item.getName())).result(); // filter the inventory to only get the items we want to drop

        for (int i = 0; i < Math.min(itemsToDrop.size(), 10); i++) {
            InventoryInteraction.useItem(itemsToDrop.get(i), "Drop"); // we'll loop through this at a max of 10 times.  can make this a config options.  drops x items per tick (x = 10 in this example)
        }
    }

    private boolean isInventoryReset() {
        return Inventory.search()
                .filter(item -> !shouldKeep(item.getName())) // using our shouldKeep method, we can filter the items here to only include the ones we want to drop.
                .result()
                .size() == 28 - config.emptySlots(); // we will know that the inventory is reset if the size becomes the amount of slots - empty slots
    }

    private boolean isDroppingItems() {
        return state == State.DROP_ITEMS; // if the user is dropping items, we don't want it to proceed until they're all dropped.
    }

    private boolean shouldKeep(String name) {
        String[] itemsToKeep = config.itemsToKeep().split(","); // split the items listed by comma, no space.

        return Arrays.stream(itemsToKeep) // stream the array using Arrays.stream() from java.util
                .anyMatch(i -> name.toLowerCase().contains(i.toLowerCase())); // we'll set everything to lowercase, and check if the input name contains any of the items in the itemsToKeep array.
                // might seem silly, but this is to allow specific items you want to keep without typing the full name.  I also prefer names over ids- you can change this if you like.
    }

    private boolean hasTools() {
        String[] tools = config.toolsToUse().split(","); // split the tools listed by comma, no space.

        return Inventory.search()
                .filter(item -> isTool(item.getName())) // filter inventory by using out isTool method
                .result().size() == tools.length; // if the size of tools and the filtered inventory is the same, we have our tools.
    }

    private boolean isTool(String name) {
        String[] tools = config.toolsToUse().split(","); // split the tools listed by comma, no space.

        return Arrays.stream(tools) // stream the array using Arrays.stream() from java.util
                .anyMatch(i -> name.toLowerCase().contains(i.toLowerCase())); // more likely for user error than the shouldKeep option, but we'll follow the same idea as shouldKeep.
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
