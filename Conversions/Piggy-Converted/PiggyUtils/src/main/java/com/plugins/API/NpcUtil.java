package com.plugins.API;

import com.plugins.Collections.NPCs;
import com.plugins.Collections.query.NPCQuery;
import net.runelite.api.NPC;

import java.util.Optional;

public class NpcUtil {

    public static Optional<NPC> getNpc(String name, boolean caseSensitive) {
        if (caseSensitive) {
            return NPCs.search().withName(name).nearestToPlayer();
        } else {
            return nameContainsNoCase(name).nearestToPlayer();
        }
    }

    public static NPCQuery nameContainsNoCase(String name) {
        return NPCs.search().filter(npcs -> npcs.getName() != null && npcs.getName().toLowerCase().contains(name.toLowerCase()));
    }

}
