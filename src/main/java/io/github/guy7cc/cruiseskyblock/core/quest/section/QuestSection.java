package io.github.guy7cc.cruiseskyblock.core.quest.section;

import io.github.guy7cc.cruiseskyblock.core.system.Tickable;
import io.github.guy7cc.cruiseskyblock.core.quest.QuestState;

public abstract class QuestSection implements Tickable {
    public final QuestState state;
    private int triggeredTick;
    private boolean finished;

    public QuestSection(QuestState state) {
        this.state = state;
    }

    public void trigger(int globalTick) {
        triggeredTick = globalTick;
    }

    public void tick(int globalTick) {

    }

    public boolean isFinished() {
        return finished;
    }
}
