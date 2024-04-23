package io.github.guy7cc.cruiseskyblock.core.quest.section;

import io.github.guy7cc.cruiseskyblock.core.quest.QuestState;
import io.github.guy7cc.cruiseskyblock.core.spawner.CustomSpawnerState;

import java.util.List;

public class SpawnerSection extends QuestSection {
    private final List<CustomSpawnerState> spawners;

    public SpawnerSection(QuestState state, List<CustomSpawnerState> spawners) {
        super(state);
        this.spawners = spawners;
    }
}
