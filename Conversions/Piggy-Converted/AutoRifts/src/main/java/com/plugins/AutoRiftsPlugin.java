package com.plugins;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.plugins.data.Altar;
import com.plugins.data.Constants;
import com.plugins.data.State;
import com.plugins.data.Utility;
import com.plugins.API.InventoryUtil;
import com.plugins.API.ObjectUtil;
import com.plugins.BreakHandler.ReflectBreakHandler;
import com.plugins.Collections.NPCs;
import com.plugins.Collections.TileObjects;
import com.plugins.Collections.Widgets;
import com.plugins.EthanApiPlugin;
import com.plugins.InteractionApi.InventoryInteraction;
import com.plugins.InteractionApi.NPCInteraction;
import com.plugins.InteractionApi.TileObjectInteraction;
import com.plugins.PacketUtilsPlugin;
import com.plugins.Packets.MousePackets;
import com.plugins.Packets.MovementPackets;
import com.plugins.Packets.ObjectPackets;
import com.plugins.Packets.TileItemPackets;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.AnimationID;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.GameState;
import net.runelite.api.NPC;
import net.runelite.api.Skill;
import net.runelite.api.TileObject;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.HotkeyListener;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@PluginDependency(PacketUtilsPlugin.class)
@PluginDependency(EthanApiPlugin.class)
@PluginDescriptor(
        name = "<html><font color=\"#FF9DF9\">[PP]</font> Auto Rifts</html>",
        description = "Guardians of the Rift",
        enabledByDefault = false,
        tags = {"ethan", "piggy"}
)
@Slf4j
public class AutoRiftsPlugin extends Plugin {

    private static final Set<Integer> MINING_ANIMATION_IDS = Set.of(AnimationID.MINING_ADAMANT_PICKAXE, AnimationID.MINING_TRAILBLAZER_PICKAXE, AnimationID.MINING_BLACK_PICKAXE, AnimationID.MINING_BRONZE_PICKAXE, AnimationID.MINING_CRYSTAL_PICKAXE, AnimationID.MINING_DRAGON_PICKAXE, AnimationID.MINING_GILDED_PICKAXE, AnimationID.MINING_INFERNAL_PICKAXE, AnimationID.MINING_MITHRIL_PICKAXE, AnimationID.MINING_RUNE_PICKAXE, AnimationID.MINING_DRAGON_PICKAXE_OR, AnimationID.MINING_DRAGON_PICKAXE_OR_TRAILBLAZER, AnimationID.MINING_DRAGON_PICKAXE_UPGRADED, AnimationID.MINING_IRON_PICKAXE, AnimationID.MINING_STEEL_PICKAXE, AnimationID.MINING_TRAILBLAZER_PICKAXE_3, AnimationID.MINING_TRAILBLAZER_PICKAXE_2, AnimationID.MINING_3A_PICKAXE);

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
    private OverlayManager overlayManager;
    @Inject
    private KeyManager keyManager;
    @Inject
    private AutoRiftsOverlay overlay;
    @Inject
    private AutoRiftsConfig config;

    @Provides
    private AutoRiftsConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(AutoRiftsConfig.class);
    }

    private static final Set<Integer> GOTR_REGIONS = Set.of(14483, 14484);

    @Getter(AccessLevel.PACKAGE)
    private final Set<GameObject> guardians = new HashSet<>();
    @Getter(AccessLevel.PACKAGE)
    private final Set<GameObject> activeGuardians = new HashSet<>();
    @Getter
    private State state;
    private int timeout;
    private boolean gameStarted;
    private TileObject catalyticAltar;
    private TileObject elementalAltar;
    private Set<Altar> accessibleAltars;
    @Getter
    private boolean started;
    private Instant timer;
    private long pauseTime;
    private boolean attackStarted;

    @Override
    protected void startUp() throws Exception {
        this.overlayManager.add(overlay);
        this.accessibleAltars = Utility.getAccessibleAltars(client.getRealSkillLevel(Skill.RUNECRAFT),
                config.cosmicRunes(), config.lawRunes(), config.deathRunes(), config.bloodRunes());
        this.keyManager.registerKeyListener(this.toggle);
        this.breakHandler.registerPlugin(this);
        this.timer = Instant.now();
    }

    @Override
    protected void shutDown() throws Exception {
        this.keyManager.unregisterKeyListener(this.toggle);
        this.breakHandler.unregisterPlugin(this);
        this.breakHandler.stopPlugin(this);
        this.overlayManager.remove(overlay);
    }

    @Subscribe
    private void onGameTick(GameTick event) {
        if (client.getGameState() != GameState.LOGGED_IN
                || !started) {
            return;
        }

        if (!gameStarted && isWidgetVisible()) {
            gameStarted = true;
        }

        if (!isInAltar() && !isWidgetVisible()) {
            gameStarted = false;
            attackStarted = false;
        }

        this.accessibleAltars = Utility.getAccessibleAltars(client.getRealSkillLevel(Skill.RUNECRAFT),
                config.cosmicRunes(), config.lawRunes(), config.deathRunes(), config.bloodRunes());

        state = getCurrentState();
        handleState();
    }

    @Subscribe
    private void onChatMessage(ChatMessage event) {
        if (client.getGameState() != GameState.LOGGED_IN) {
            return;
        }

        if (event.getMessage().contains(Constants.GAME_STARTED)) {
            gameStarted = true;
        }

        if (event.getMessage().contains(Constants.GAME_OVER)) {
            gameStarted = false;
            attackStarted = false;
        }

        if (event.getMessage().contains(Constants.GAME_WIN)) {
            gameStarted = false;
            attackStarted = false;
        }

        if (event.getMessage().contains(Constants.ATTACK_STARTED)) {
            attackStarted = true;
        }
    }

    private int tickDelay() {
        return ThreadLocalRandom.current().nextInt(0, 3);
    }

    private void handleState() {
        switch (state) {
            case OUTSIDE_BARRIER:
                enterGame();
                break;
            case WAITING_FOR_GAME:
                waitForGame();
                break;
            case RETURN_TO_START:
                climbLargeMine();
                break;
            case MINE_LARGE:
                mineLargeGuardians();
                break;
            case MINE_HUGE:
                mineHugeGuardians();
                break;
            case MINE_GAME:
                mineGameGuardians();
                break;
            case CRAFT_ESSENCE:
                craftEssence();
                break;
            case BUILD_GUARDIAN:
                break;
            case CHARGE_SHIELD:
                break;
            case DEPOSIT_RUNES:
                depositRunes();
                break;
            case ENTER_PORTAL:
                enterPortal();
                break;
            case ENTER_RIFT:
                enterRift();
                break;
            case CRAFT_RUNES:
                craftRunes();
                break;
            case EXIT_ALTAR:
                exitAltar();
                timeout = tickDelay();
                break;
            case POWER_GUARDIAN:
                powerGuardian();
                break;
            case REPAIR_POUCH:
                break;
            case TAKE_CELLS:
                takeCells();
                break;
            case DROP_RUNES:
                dropRunes();
                break;
            case DROP_TALISMAN:
                dropTalisman();
                break;
        }
    }

    private void dropTalisman() {
        Optional<Widget> itemWidget = InventoryUtil.nameContainsNoCase("talisman").first();
        if (itemWidget.isEmpty()) {
            return;
        }

        Widget item = itemWidget.get();
        InventoryInteraction.useItem(item, "Drop");
    }

    private void dropRunes() {
        Optional<Widget> itemWidget = InventoryUtil.nameContainsNoCase("rune").filter(item -> !item.getName().contains("pickaxe")).first();
        if (itemWidget.isEmpty()) {
            return;
        }

        Widget item = itemWidget.get();
        InventoryInteraction.useItem(item, "Drop");
    }

    private void depositRunes() {
        Optional<TileObject> tileObject = TileObjects.search().withAction("Deposit-runes").nearestToPlayer();
        if (tileObject.isEmpty()) {
            return;
        }

        TileObject runeDeposit = tileObject.get();
        TileObjectInteraction.interact(runeDeposit, "Deposit-runes");
    }

    private void powerGuardian() {
        Optional<NPC> npc = NPCs.search().nameContains(Constants.GREAT_GUARDIAN).nearestToPlayer();
        if (npc.isEmpty()) {
            return;
        }

        NPC guardian = npc.get();
        NPCInteraction.interact(guardian, "Power-up");
    }

    private void exitAltar() {
        Optional<TileObject> tileObject = TileObjects.search().nameContains(Constants.PORTAL).nearestToPlayer();
        if (tileObject.isEmpty()) {
            return;
        }

        TileObject portal = tileObject.get();
        TileObjectInteraction.interact(portal, "Use");
    }

    private void craftRunes() {
        Optional<TileObject> tileObject = TileObjects.search().withAction(Constants.CRAFT_RUNES).nearestToPlayer();
        if (tileObject.isEmpty()) {
            return;
        }

        TileObject altar = tileObject.get();
        TileObjectInteraction.interact(altar, Constants.CRAFT_RUNES);
    }

    private void enterRift() {
        int elemental = client.getVarbitValue(13686);
        int catalytic = client.getVarbitValue(13685);

        Widget catalyticWidget = client.getWidget(48889879);
        Widget elementalWidget = client.getWidget(48889876);

        if (elementalWidget == null || catalyticWidget == null) {
            return;
        }

        Altar elementalAltar = Altar.getAltarBySpriteId(elementalWidget.getSpriteId());
        Altar catalyticAltar = Altar.getAltarBySpriteId(catalyticWidget.getSpriteId());

        if (elementalAltar == null || catalyticAltar == null) {
            return;
        }

        ObjectUtil.nameContainsNoCase("Guardian of").filter(tileObject -> {
            return tileObject.getId() == elementalAltar.getId() && accessibleAltars.contains(elementalAltar);
        }).nearestToPlayer().ifPresent(tileObject -> this.elementalAltar = tileObject);

        ObjectUtil.nameContainsNoCase("Guardian of").filter(tileObject -> { // checking by id didn't return far away guardians? weird
            return tileObject.getId() == catalyticAltar.getId() && accessibleAltars.contains(catalyticAltar); // least this works?
        }).nearestToPlayer().ifPresent(tileObject -> this.catalyticAltar = tileObject);

        if (catalytic <= elemental && accessibleAltars.contains(catalyticAltar)) {
            TileObjectInteraction.interact(this.catalyticAltar, "Enter", "Use");
        } else {
            TileObjectInteraction.interact(this.elementalAltar, "Enter", "Use");
        }
    }

    private void enterPortal() {
        Optional<TileObject> tileObject = ObjectUtil.nameContainsNoCase(Constants.PORTAL).filter(to -> to.getWorldLocation().getY() >= Constants.OUTSIDE_BARRIER_Y).nearestToPlayer();
        if (tileObject.isEmpty()) {
            return;
        }

        TileObject portal = tileObject.get();
        TileObjectInteraction.interact(portal, "Enter", "Exit", "Use");
    }

    private void craftEssence() {
        Optional<TileObject> tileObject = TileObjects.search().nameContains(Constants.WORKBENCH).nearestToPlayer();
        if (tileObject.isEmpty()) {
            return;
        }

        TileObject workbench = tileObject.get();
        TileObjectInteraction.interact(workbench, "Work-at");
    }

    private void takeCells() {
        Optional<TileObject> tileObject = TileObjects.search().withAction("Take-10").nearestToPlayer();
        if (tileObject.isEmpty()) {
            return;
        }

        TileObject unchargedCells = tileObject.get();
        TileObjectInteraction.interact(unchargedCells, "Take-10");
    }

    private void climbLargeMine() {
        Optional<TileObject> tileObject = TileObjects.search().withAction("Climb").nearestToPlayer();
        if (tileObject.isEmpty()) {
            return;
        }

        TileObject rubble = tileObject.get();
        TileObjectInteraction.interact(rubble, "Climb");
    }

    private void mineHugeGuardians() {
        Optional<TileObject> tileObject = TileObjects.search().nameContains(Constants.HUGE_REMAINS).nearestToPlayer();
        if (tileObject.isEmpty()) {
            return;
        }

        TileObject remains = tileObject.get();
        TileObjectInteraction.interact(remains, "Mine");
    }

    private void mineLargeGuardians() {
        Optional<TileObject> tileObject = TileObjects.search().nameContains(Constants.LARGE_REMAINS).nearestToPlayer();
        if (tileObject.isEmpty()) {
            return;
        }

        TileObject remains = tileObject.get();
        TileObjectInteraction.interact(remains, "Mine");
    }

    private void mineGameGuardians() {
        Optional<TileObject> tileObject = ObjectUtil.nameContainsNoCase(Constants.GAME_PARTS).nearestToPlayer();
        if (tileObject.isEmpty()) {
            return;
        }

        TileObject remains = tileObject.get();
        TileObjectInteraction.interact(remains, "Mine");
    }

    private void enterGame() {
        Optional<TileObject> tileObject = TileObjects.search().nameContains("Barrier").withAction("Quick-pass").nearestToPlayer();
        if (tileObject.isEmpty()) {
            return;
        }

        TileObject barrier = tileObject.get();
        TileObjectInteraction.interact(barrier, "Quick-pass");
    }

    private void waitForGame() {
        if (client.getLocalPlayer().getWorldLocation().getX() == Constants.LARGE_MINE_X) {
            MousePackets.queueClickPacket();
            MovementPackets.queueMovement(3639, 9500, false);
        }
    }

    public State getCurrentState() {
        if ((EthanApiPlugin.isMoving() || client.getLocalPlayer().getAnimation() != -1) && !isMining()) {
            if (isInAltar()) {
                if (!hasAnyGuardianEssence()) {
                    return State.EXIT_ALTAR;
                }
                if (!gameStarted) {
                    return State.EXIT_ALTAR;
                }
            }
            if (isCraftingEss() && !isPortalSpawned()) {
                return State.CRAFTING_ESS;
            }
            return State.ANIMATING;
        }

        if (isInAltar()) {
            if (!hasAnyGuardianEssence()) {
                return State.EXIT_ALTAR;
            }
            if (!gameStarted) {
                return State.EXIT_ALTAR;
            }
        }

        if (timeout > 0 && state != State.WAITING) {
            timeout--;
            return State.TIMEOUT;
        }

        if (isGameBusy()) {
            return State.GAME_BUSY;
        }

        if (isOutsideBarrier() && !isInAltar() && !isGameBusy()) {
            return State.OUTSIDE_BARRIER;
        }

//        if (config.makeGuardians() && !hasUnchargedCells()) {
//            return State.TAKE_CELLS;
//        }

        if (!gameStarted && !isInLargeMine() && client.getLocalPlayer().getInteracting() == null) {
            return State.RETURN_TO_START;
        }

        if (!gameStarted && !isInAltar()) {
            return State.WAITING_FOR_GAME;
        }

        if (hasTalisman()) {
            return State.DROP_TALISMAN;
        }

        if (shouldDepositRunes() && !isInHugeMine() && !isInLargeMine()) {
            if (config.dropRunes()) {
                return State.DROP_RUNES;
            }
            return State.DEPOSIT_RUNES;
        }

        if (isPortalSpawned() && !isInHugeMine() && !hasGuardianEssence()) {
            return State.ENTER_PORTAL;
        }

        if (!hasEnoughFrags() && !isCraftingEss()) {
            if (isInLargeMine()) {
                return isMining() ? State.MINING : State.MINE_LARGE;
            }

            if (isInHugeMine()) {
                if (hasGuardianEssence()) {
                    return State.ENTER_PORTAL;
                }
                return isMining() ? State.MINING : State.MINE_HUGE;
            }

            if (hasGuardianEssence()) {
                if (hasPowerEssence()) {
                    return State.POWER_GUARDIAN;
                }
                if (isInAltar()) {
                    return State.CRAFT_RUNES;
                }
                return State.ENTER_RIFT;
            }

            if (hasPowerEssence()) {
                if (isPortalSpawned() && !hasGuardianEssence()) {
                    return State.ENTER_PORTAL;
                }
                return State.POWER_GUARDIAN;
            }

            if (isPortalSpawned()) {
                return State.ENTER_PORTAL;
            }

            return isMining() ? State.MINING : State.MINE_GAME;
        }

        if (hasEnoughFrags()) {
            if(isInLargeMine()) {
                if (isMining() && !hasEnoughStartingFrags()) {
                    return State.MINING;
                }

                return !hasEnoughStartingFrags() ? State.MINE_LARGE : State.RETURN_TO_START;
            }

            if (isInHugeMine()) {
                if (hasGuardianEssence()) {
                    return State.ENTER_PORTAL;
                }

                return !isMining() ? State.MINE_HUGE : State.MINING;
            }

            if (!hasGuardianEssence() && !hasPowerEssence()) {
                if (isPortalSpawned()) {
                    return State.ENTER_PORTAL;
                }
                return isCraftingEss() ? State.CRAFTING_ESS :  State.CRAFT_ESSENCE;
            }

            if (hasGuardianEssence()) {
                if (hasPowerEssence()) {
                    return State.POWER_GUARDIAN;
                }
                if (isInAltar()) {
                    return State.CRAFT_RUNES;
                }
                return State.ENTER_RIFT;
            }

            if (hasPowerEssence() && !isInAltar()) {
                if (!hasGuardianEssence()) {
                    if (isPortalSpawned()) {
                        return State.ENTER_PORTAL;
                    }
                    return isCraftingEss() ? State.CRAFTING_ESS :  State.CRAFT_ESSENCE;
                }
                return State.POWER_GUARDIAN;
            }
        }

        return State.WAITING;
    }

    private boolean hasTalisman() {
        return InventoryUtil.nameContainsNoCase("talisman").first().isPresent();
    }

    private boolean hasUnchargedCells() {
        return InventoryUtil.hasItem(Constants.UNCHARGED_CELLS);
    }

    private boolean hasPowerEssence() {
        boolean e = InventoryUtil.hasItem(Constants.CATALYTIC_ENERGY) || InventoryUtil.hasItem(Constants.ELEMENTAL_ENERGY);
        log.info(e + ", hpe");
        return e;
    }

    private boolean shouldDepositRunes() {
        return !InventoryUtil.nameContainsNoCase("rune").filter(item -> !item.getName().contains("pouch")).empty();
    }

    private boolean isPortalSpawned() {
        return ObjectUtil.nameContainsNoCase(Constants.PORTAL).filter(tileObject -> tileObject.getWorldLocation().getY() > Constants.OUTSIDE_BARRIER_Y).nearestToPlayer().isPresent();
    }

    private boolean hasGuardianEssenceAmount(int amount) {
        return InventoryUtil.getItemAmount(Constants.ESS, false) >= amount;
    }

    private boolean hasGuardianEssence() {
        int amt = InventoryUtil.getItemAmount(Constants.ESS, false);
        log.info(amt + ",hge");
        return amt >= config.emptySlots();
    }

    private boolean hasAnyGuardianEssence() {
        return InventoryUtil.getItemAmount(Constants.ESS, false) >= 1;
    }

    private boolean hasEnoughFrags() {
        return InventoryUtil.hasItem(Constants.FRAGS, config.minFrags(), true);
    }

    private boolean hasEnoughStartingFrags() {
        return InventoryUtil.hasItem(Constants.FRAGS, config.startingFrags(), true);
    }

    private boolean isWidgetVisible() {
        Optional<Widget> widget = Widgets.search().withId(Constants.PARENT_WIDGET).first();
        return widget.isPresent() && !widget.get().isHidden();
    }

    private boolean isMining() {
        return MINING_ANIMATION_IDS.contains(client.getLocalPlayer().getAnimation());
    }

    private boolean isOutsideBarrier() {
        return client.getLocalPlayer().getWorldLocation().getY() <= Constants.OUTSIDE_BARRIER_Y && !isInAltar();
    }

    private boolean isInLargeMine() {
        return !isInAltar() && client.getLocalPlayer().getWorldLocation().getX() >= Constants.LARGE_MINE_X;
    }

    private boolean isInHugeMine() {
        return !isInAltar() && client.getLocalPlayer().getWorldLocation().getX() <= Constants.HUGE_MINE_X;
    }

    private boolean isGameBusy() {
        return isOutsideBarrier() && NPCs.search().withId(Constants.BARRIER_BUSY_ID).nearestToPlayer().isPresent();
    }

    private boolean isInAltar() {
        for (int region : client.getMapRegions()) {
            if (GOTR_REGIONS.contains(region)) {
                return false;
            }
        }
        return true;
    }

    private int getFrags() {
        return InventoryUtil.getItemAmount(Constants.FRAGS, true);
    }

    private boolean isCraftingEss() {
        return client.getLocalPlayer().getAnimation() == 9365;
    }

    public long getElapsedTimeMs() {
        Duration duration = Duration.between(timer, Instant.now());
        return duration.toMillis() + pauseTime;
    }

    public String getElapsedTime() {
        if (!started) {
            long second = (pauseTime / 1000) % 60;
            long minute = (pauseTime / (1000 * 60)) % 60;
            long hour = (pauseTime / (1000 * 60 * 60)) % 24;
            return String.format("%02d:%02d:%02d", hour, minute, second);
        }
        Duration duration = Duration.between(timer, Instant.now());
        long durationInMillis = duration.toMillis() + pauseTime;
        long second = (durationInMillis / 1000) % 60;
        long minute = (durationInMillis / (1000 * 60)) % 60;
        long hour = (durationInMillis / (1000 * 60 * 60)) % 24;

        return String.format("%02d:%02d:%02d", hour, minute, second);
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

        if (!started) {
            pauseTime = getElapsedTimeMs();
            breakHandler.stopPlugin(this);
        } else {
            breakHandler.startPlugin(this);
            timer = Instant.now();
        }
    }
}
