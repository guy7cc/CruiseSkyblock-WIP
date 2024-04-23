package io.github.guy7cc.cruiseskyblock.core.role;

import io.github.guy7cc.cruiseskyblock.core.system.Registry;

public class Roles {
    public static final Registry<Role> REGISTRY = new Registry<>("role");

    public static final Role STANDARD = REGISTRY.register(new Role(0, "standard", '\uE000'));
    public static final Role NORMAL_TANK = REGISTRY.register(new Role(1, "normal_tank", '\uE001'));
    public static final Role NORMAL_ATTACKER = REGISTRY.register(new Role(2, "normal_attacker", '\uE002'));
    public static final Role NORMAL_SUPPORTER = REGISTRY.register(new Role(3, "normal_supporter", '\uE003'));
}
