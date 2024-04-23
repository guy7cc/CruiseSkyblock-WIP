package io.github.guy7cc.cruiseskyblock.core.system;

/**
 * @see BaseSelectiveTicker
 */
public interface ConditionalTickable extends Tickable {
    boolean shouldGoToIndex();

    boolean shouldGoToQueue();

    boolean shouldBeRemoved();
}
