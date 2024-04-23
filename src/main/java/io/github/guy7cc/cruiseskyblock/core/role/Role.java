package io.github.guy7cc.cruiseskyblock.core.role;

import io.github.guy7cc.cruiseskyblock.core.system.RegistryObject;

public class Role extends RegistryObject {
    public final char icon;

    public Role(int id, String name, char icon) {
        super(id, name);
        this.icon = icon;
    }
}
