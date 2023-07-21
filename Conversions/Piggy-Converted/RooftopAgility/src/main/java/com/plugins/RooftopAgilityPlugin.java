package com.plugins;

import com.plugins.API.BankUtil;
import com.plugins.API.InventoryUtil;
import com.plugins.BreakHandler.ReflectBreakHandler;
import com.plugins.Collections.Bank;
import com.plugins.Collections.ETileItem;
import com.plugins.Collections.Inventory;
import com.plugins.Collections.TileObjects;
import com.plugins.Collections.Widgets;
import com.plugins.Collections.query.TileObjectQuery;
import com.plugins.EthanApiPlugin;
import com.plugins.InteractionApi.BankInteraction;
import com.plugins.InteractionApi.InventoryInteraction;
import com.plugins.InteractionApi.TileObjectInteraction;
import com.plugins.PacketUtilsPlugin;
import com.plugins.Packets.MousePackets;
import com.plugins.Packets.MovementPackets;
import com.plugins.Packets.ObjectPackets;
import com.plugins.Packets.TileItemPackets;
import com.plugins.Packets.WidgetPackets;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.plugins.PiggyUtilsPlugin;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.GameState;
import net.runelite.api.ItemID;
import net.runelite.api.ObjectComposition;
import net.runelite.api.Player;
import net.runelite.api.Skill;
import net.runelite.api.Tile;
import net.runelite.api.TileItem;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.ItemDespawned;
import net.runelite.api.events.ItemSpawned;
import net.runelite.api.kit.KitType;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.HotkeyListener;

import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@PluginDescriptor(
        name = "<html><font color=\"#FF9DF9\">[PP]</font> Rooftop Agility</html>",
        description = "Partially ported iRooftops (by illumine), example of how ReflectBreakHandler can be used for chinbreakhandler",
        enabledByDefault = false,
        tags = {"ethan", "piggy"}
)
@PluginDependency(EthanApiPlugin.class)
@PluginDependency(PacketUtilsPlugin.class)
@PluginDependency(PiggyUtilsPlugin.class)
@Slf4j
public class RooftopAgilityPlugin extends Plugin {
    @Inject
    private Client client;
    @Inject
    private EthanApiPlugin api;
    @Inject
    private MovementPackets movementPackets;
    @Inject
    private ObjectPackets objectPackets;
    @Inject
    private MousePackets mousePackets;
    @Inject
    private TileItemPackets tileItemPackets;
    @Inject
    private ReflectBreakHandler breakHandler;
    @Inject
    private RooftopAgilityConfig config;
    @Inject
    private OverlayManager overlayManager;
    @Inject
    private RooftopAgilityOverlay overlay;
    @Inject
    private KeyManager keyManager;
    @Inject
    private ChatMessageManager messageManager;

    private Player player;
    @Getter
    private State state;
    private Instant timer;
    private TileItem markOfGrace;
    private Portals priffPortal;
    private GameObject spawnedPortal;
    private int timeout;
    private int mogSpawnCount;
    @Getter
    private int mogCollectCount;
    private int mogInventoryCount = -1;
    private int marksPerHour;
    @Getter
    private boolean startAgility;
    private Tile markOfGraceTile;

    private WorldPoint CAMELOT_TELE_LOC = new WorldPoint(2705, 3463, 0);

    private final Set<Integer> REGION_IDS = Set.of(9781, 12853, 12597, 12084, 12339, 12338, 10806, 10297, 10553, 13358, 13878, 10547, 13105, 9012, 9013, 12895, 13151, 13152, 11050, 10794);
    private final Set<Integer> AIR_STAFFS = Set.of(ItemID.STAFF_OF_AIR, ItemID.AIR_BATTLESTAFF, ItemID.DUST_BATTLESTAFF, ItemID.MIST_BATTLESTAFF,
            ItemID.SMOKE_BATTLESTAFF, ItemID.MYSTIC_AIR_STAFF, ItemID.MYSTIC_DUST_STAFF, ItemID.MYSTIC_SMOKE_STAFF, ItemID.MYSTIC_MIST_STAFF);

    public long getMarksPH() {
        Duration timeSinceStart = Duration.between(timer, Instant.now());
        if (!timeSinceStart.isZero()) {
            return (int) ((double) mogCollectCount * (double) Duration.ofHours(1).toMillis() / (double) timeSinceStart.toMillis());
        }
        return 0;
    }

    private int tickDelay() {
        return config.tickDelay() ? ThreadLocalRandom.current().nextInt(config.tickDelayMin(), config.tickDelayMax()) : 0;
    }

    private void findObstacle() {
        Obstacles obstacle = Obstacles.getObstacle(client.getLocalPlayer().getWorldLocation());
        if (obstacle != null) {
            Optional<TileObject> tileObject = TileObjects.search().withId(obstacle.getObstacleId()).first();
            if (tileObject.isPresent() && !client.getLocalPlayer().isInteracting()) {
                TileObjectInteraction.interact(tileObject.get(), TileObjectQuery.getObjectComposition(tileObject.get()).getActions()[0]);
            }
        }
    }

    private boolean runIsOff() {
        return EthanApiPlugin.getClient().getVarpValue(173) == 0;
    }

    private State getCurrentState() {
        if (breakHandler.shouldBreak(this)) {
            return State.HANDLE_BREAK;
        }
        if (timeout > 0) {
            if (shouldRestock()) {
                return State.RESTOCK_ITEMS;
            }
            if (!config.foodName().isEmpty()
                    && client.getBoostedSkillLevel(Skill.HITPOINTS) < config.lowHP()) {
                Optional<Widget> food = InventoryUtil.nameContainsNoCase(config.foodName()).first();
                if (food.isPresent()) {
                    return State.EAT_FOOD;
                } else {
                    return State.RESTOCK_ITEMS;
                }
            }
            if (runIsOff() && client.getEnergy() >= config.enableRun() * 100) {
                return State.ENABLE_RUN;
            }

            return State.TIMEOUT;
        }
        if (shouldCastTeleport()) {
            return State.CAST_CAMELOT_TELEPORT;
        }

        if (EthanApiPlugin.isMoving()) {
            timeout = tickDelay();
            return State.MOVING;
        }
        if (shouldEatSummerPie()) {
            return State.EAT_SUMMER_PIE;
        }
        Obstacles currentObstacle = Obstacles.getObstacle(client.getLocalPlayer().getWorldLocation());
        if (currentObstacle == null) {
            timeout = tickDelay();
            return State.MOVING;
        }
        if (currentObstacle.getBankID() > 0 && shouldRestock()) {
            if (TileObjects.search().withId(currentObstacle.getBankID()).nearestToPlayer().isPresent()) {
                return State.RESTOCK_ITEMS;
            }
        }
        if (markOfGrace != null && markOfGraceTile != null && config.mogPickup() && (!Inventory.full() || Inventory.getItemAmount(ItemID.MARK_OF_GRACE) > 0)) {
            if (currentObstacle.getLocation().distanceTo(markOfGraceTile.getWorldLocation()) == 0) {
                if (markOfGraceTile.getGroundItems().contains(markOfGrace)) {
                    return State.MARK_OF_GRACE;
                } else {
                    markOfGrace = null;
                }
            }
        }

        // might not work, can't test it rn unfortunately - will get back to this later
        if (client.getVarbitValue(9298) != 0) {
            priffPortal = Portals.getPortal(client.getVarbitValue(9298));
            Optional<TileObject> portal = TileObjects.search().withId(priffPortal.getPortalID()).first();
            if (portal.isPresent()) {
                spawnedPortal = (GameObject) portal.get();
                if (currentObstacle.getLocation().distanceTo(spawnedPortal.getWorldLocation()) == 0) {
                    return State.PRIFF_PORTAL;
                }
            }
        }
        if (!EthanApiPlugin.isMoving()) {
            return State.FIND_OBSTACLE;
        }
        return State.ANIMATING;
    }

    private void eatFood() {
        Optional<Widget> cake = InventoryUtil.nameContainsNoCase(config.foodName()).first();
        if (cake.isPresent()) {
            MousePackets.queueClickPacket();
            InventoryInteraction.useItem(cake.get(), "Eat");
        }
    }

    public String getElapsedTime() {
        Duration duration = Duration.between(timer, Instant.now());
        long durationInMillis = duration.toMillis();
        long second = (durationInMillis / 1000) % 60;
        long minute = (durationInMillis / (1000 * 60)) % 60;
        long hour = (durationInMillis / (1000 * 60 * 60)) % 24;

        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    private void resetVals() {
        markOfGraceTile = null;
        markOfGrace = null;
        startAgility = false;
        timer = null;
        mogSpawnCount = 0;
        mogCollectCount = 0;
        mogInventoryCount = -1;
        marksPerHour = 0;
    }

    private boolean shouldEatSummerPie() {
        return config.boostWithPie() &&
                (client.getBoostedSkillLevel(Skill.AGILITY) < config.pieLevel()) &&
                (InventoryUtil.getItemAmount(ItemID.SUMMER_PIE) > 0 || InventoryUtil.getItemAmount(ItemID.HALF_A_SUMMER_PIE) > 0);
    }

    private boolean shouldCastTeleport() {
        return config.camelotTeleport() && client.getBoostedSkillLevel(Skill.MAGIC) >= 45 &&
                CAMELOT_TELE_LOC.distanceTo(client.getLocalPlayer().getWorldLocation()) <= 3 &&
                (Inventory.getItemAmount(ItemID.LAW_RUNE) > 0 && Inventory.getItemAmount(ItemID.AIR_RUNE) >= 5 ||
                        Inventory.getItemAmount(ItemID.LAW_RUNE) > 0 && AIR_STAFFS.contains(client.getLocalPlayer().getPlayerComposition().getEquipmentId(KitType.WEAPON)));
    }

    private boolean shouldRestock() {
        if (config.foodName().isEmpty()) {
            return false;
        }
        return !InventoryUtil.hasItem(config.foodName());
    }

    private void restockItems() {
        if (Bank.isOpen()) {
            if (!config.foodName().isEmpty() && !InventoryUtil.hasItem(config.foodName())) {
                Optional<Widget> bankFood = BankUtil.nameContainsNoCase(config.foodName()).first();
                if (bankFood.isPresent()) {
                    BankInteraction.withdrawX(bankFood.get(), 14);
                    return;
                }

                if (!config.keepGoing()) {
                    state = State.TIMEOUT;
                    return;
                }
            }
            if (config.boostWithPie() && !InventoryUtil.hasItem("summer pie")) {
                Optional<Widget> bankPie = BankUtil.nameContainsNoCase("summer pie").first();
                if (bankPie.isPresent()) {
                    BankInteraction.withdrawX(bankPie.get(), 10);
                    return;
                }

                if (!config.keepGoing()) {
                    state = State.TIMEOUT;
                }
            }
        } else {
            Optional<TileObject> bankBooth = TileObjects.search().filter(tileObject -> {
                ObjectComposition objectComposition = TileObjectQuery.getObjectComposition(tileObject);
                return getName().toLowerCase().contains("bank") ||
                        Arrays.stream(objectComposition.getActions()).anyMatch(action -> action != null && action.toLowerCase().contains("bank"));
            }).nearestToPlayer();
            if (bankBooth.isPresent()) {
                MousePackets.queueClickPacket();
                TileObjectInteraction.interact(bankBooth.get(), "Bank");
                timeout = tickDelay();
            }
        }
    }

    private void eatSummerPie() {
        Optional<Widget> summerPieItem = Inventory.search().filter(item -> item.getItemId() == ItemID.SUMMER_PIE || item.getItemId() == ItemID.HALF_A_SUMMER_PIE).first();
        if (summerPieItem.isPresent()) {
            WidgetItem item = (WidgetItem) summerPieItem.get();
            MousePackets.queueClickPacket();
            InventoryInteraction.useItem(item.getId(), "Eat");
        }
    }

    @Provides
    private RooftopAgilityConfig provideConfig(ConfigManager manager) {
        return manager.getConfig(RooftopAgilityConfig.class);
    }

    @Override
    protected void startUp() throws Exception {
        timer = Instant.now();
        overlayManager.add(overlay);
        keyManager.registerKeyListener(agilityToggle);
        breakHandler.registerPlugin(this);
    }

    @Override
    protected void shutDown() throws Exception {
        resetVals();
        overlayManager.remove(overlay);
        breakHandler.unregisterPlugin(this);
        keyManager.unregisterKeyListener(agilityToggle);
    }

    @Subscribe
    private void onGameTick(GameTick event) {
        player = client.getLocalPlayer();
        if (player == null || !startAgility || !REGION_IDS.contains(client.getLocalPlayer().getWorldLocation().getRegionID()) || breakHandler.isBreakActive(this)) {
            return;
        }
        if (!client.isResized()) {
            ChatMessageBuilder builder = new ChatMessageBuilder();
            builder.append(Color.RED, "You must be set to resizable mode to use Void Agility.");
            String msg = builder.build();
            messageManager.queue(QueuedMessage.builder().name("VoidAgility").runeLiteFormattedMessage(msg).build());
            return;
        }
        marksPerHour = (int) getMarksPH();
        state = getCurrentState();
        switch (state) {
            case TIMEOUT:
                timeout--;
                break;
            case MARK_OF_GRACE:
                MousePackets.queueClickPacket();
                TileItemPackets.queueTileItemAction(new ETileItem(markOfGraceTile.getWorldLocation(), markOfGrace), false);
                break;
            case FIND_OBSTACLE:
                findObstacle();
                break;
            case RESTOCK_ITEMS:
                restockItems();
                break;
            case MOVING:
                break;
            case CAST_CAMELOT_TELEPORT:
                Optional<Widget> camelotSpellIcon = Widgets.search().withId(14286880).first();
                if (camelotSpellIcon.isPresent()) {
                    MousePackets.queueClickPacket();
                    WidgetPackets.queueWidgetAction(camelotSpellIcon.get(), "Seers'");
                    timeout = 2 + tickDelay();
                }
                break;
            case PRIFF_PORTAL:
                Optional<TileObject> tileObject = TileObjects.search().withId(spawnedPortal.getId()).first();
                if (tileObject.isPresent()) {
                    MousePackets.queueClickPacket();
                    TileObjectInteraction.interact(tileObject.get(), TileObjectQuery.getObjectComposition(tileObject.get().getId()).getActions()[0]);
                }
                break;
            case HANDLE_BREAK:
                breakHandler.startBreak(this);
                timeout = 10;
                break;
            case EAT_SUMMER_PIE:
                eatSummerPie();
                break;
            case EAT_FOOD:
                eatFood();
                timeout = tickDelay();
                break;
            case ENABLE_RUN:
                MousePackets.queueClickPacket();
                WidgetPackets.queueWidgetActionPacket(1, 10485787, -1, -1);
                break;
        }
    }

    @Subscribe
    private void onItemSpawned(ItemSpawned event) {
        if (!startAgility || !REGION_IDS.contains(client.getLocalPlayer().getWorldLocation().getRegionID())) {
            return;
        }

        TileItem item = event.getItem();
        Tile tile = event.getTile();

        if (item.getId() == ItemID.MARK_OF_GRACE) {
            markOfGrace = item;
            markOfGraceTile = tile;
            Optional<Widget> mog = Inventory.search().withName("Mark of Grace").first();
            mogInventoryCount = mog.map(widget -> ((WidgetItem) (widget)).getQuantity()).orElse(0);
            mogSpawnCount++;
        }
    }

    @Subscribe
    private void onItemDespawned(ItemDespawned event) {
        if (!startAgility || !REGION_IDS.contains(client.getLocalPlayer().getWorldLocation().getRegionID())) {
            return;
        }

        TileItem item = event.getItem();

        if (item.getId() == ItemID.MARK_OF_GRACE) {
            markOfGrace = null;
            markOfGraceTile = null;
        }
    }

    @Subscribe
    public void onItemContainerChanged(ItemContainerChanged event) {
        if (!startAgility || event.getContainerId() != 93 || mogInventoryCount == -1) {
            return;
        }
        if (event.getItemContainer().count(ItemID.MARK_OF_GRACE) > mogInventoryCount) {
            mogCollectCount++;
            mogInventoryCount = -1;
        }
    }

    public String getCourseName() {
        return config.course().getName();
    }

    private final HotkeyListener agilityToggle = new HotkeyListener(() -> config.toggle()) {
        @Override
        public void hotkeyPressed() {
            toggleAgility();
        }
    };

    public void toggleAgility() {
        if (client.getGameState() != GameState.LOGGED_IN) {
            return;
        }
        startAgility = !startAgility;
        if (!startAgility) {
            resetVals();
            this.state = State.TIMEOUT;
            breakHandler.stopPlugin(this);
        } else {
            breakHandler.startPlugin(this);
            timer = Instant.now();
        }
    }

}
