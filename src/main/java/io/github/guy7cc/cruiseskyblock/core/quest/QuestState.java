package io.github.guy7cc.cruiseskyblock.core.quest;

import io.github.guy7cc.cruiseskyblock.core.system.ConditionalTickable;
import org.bukkit.entity.Player;

import java.util.List;

public class QuestState implements ConditionalTickable {
    private List<Player> players;
    public final Quest quest;

    private int tick;
    private boolean setupCompleted = false;

    public QuestState(List<Player> players, Quest quest) {
        this.players = players;
        this.quest = quest;
    }

    @Override
    public void tick(int globalTick) {
        if (!setupCompleted) setupCompleted = quest.setup(tick);
        tick++;
    }

    @Override
    public boolean shouldGoToIndex() {
        return true;
    }

    @Override
    public boolean shouldGoToQueue() {
        return false;
    }

    @Override
    public boolean shouldBeRemoved() {
        return false;
    }
}
