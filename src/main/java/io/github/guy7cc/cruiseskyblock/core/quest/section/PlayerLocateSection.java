package io.github.guy7cc.cruiseskyblock.core.quest.section;

import io.github.guy7cc.cruiseskyblock.core.quest.QuestState;
import org.bukkit.util.BoundingBox;

public class PlayerLocateSection extends QuestSection {
    private BoundingBox dest;

    public PlayerLocateSection(QuestState state, BoundingBox dest) {
        super(state);
        this.dest = dest;
    }
}
