package io.github.guy7cc.cruiseskyblock.core.quest;

import io.github.guy7cc.cruiseskyblock.core.system.RegistryObject;
import org.bukkit.Location;

public abstract class Quest extends RegistryObject {
    public final Location cruiseLocation;
    public final Location returnLocation;

    public Quest(int id, String name, Location cruiseLocation, Location returnLocation) {
        super(id, name);
        this.cruiseLocation = cruiseLocation;
        this.returnLocation = returnLocation;
    }

    public boolean setup(int tick) {
        return true;
    }
}
