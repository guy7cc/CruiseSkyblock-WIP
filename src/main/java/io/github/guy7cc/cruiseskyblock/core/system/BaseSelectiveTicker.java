package io.github.guy7cc.cruiseskyblock.core.system;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.Queue;

public class BaseSelectiveTicker<T extends ConditionalTickable> implements Tickable {
    protected final ConditionalTickable[] inTick;
    protected int vacantCount;
    protected final Queue<ConditionalTickable> queue = new ArrayDeque<>();

    public BaseSelectiveTicker(int size) {
        inTick = new ConditionalTickable[size];
        vacantCount = size;
    }

    public void add(@NotNull T tickable) {
        queue.add(tickable);
    }

    public void remove(T tickable) {
        for (int i = 0; i < inTick.length; i++) {
            if (inTick[i] == tickable) {
                indexToNone(i);
            }
        }
        queue.remove(tickable);
    }

    public void tick(int globalTick) {
        for (int i = 0; i < inTick.length; i++) {
            if (inTick[i] != null) {
                inTick[i].tick(globalTick);
                if (inTick[i].shouldBeRemoved()) {
                    indexToNone(i);
                } else if (inTick[i].shouldGoToQueue()) {
                    indexToQueue(i);
                }
            }
        }

        if (queue.isEmpty()) return;
        if (vacantCount > 0 && queue.peek().shouldGoToIndex()) {
            for (int i = 0; i < inTick.length; i++) {
                if (inTick[i] == null) {
                    queueToIndex(i);
                    return;
                }
            }
        } else {
            queue.add(queue.poll());
        }
    }

    public int getVacantCount() {
        return vacantCount;
    }

    protected void queueToIndex(int index) {
        inTick[index] = queue.poll();
        vacantCount--;
    }

    protected void indexToQueue(int index) {
        queue.add(inTick[index]);
        inTick[index] = null;
        vacantCount++;
    }

    protected void indexToNone(int index) {
        inTick[index] = null;
        vacantCount++;
    }
}
