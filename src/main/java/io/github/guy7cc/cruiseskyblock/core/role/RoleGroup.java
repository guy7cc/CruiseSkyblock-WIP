package io.github.guy7cc.cruiseskyblock.core.role;

import java.util.HashSet;
import java.util.Set;

public enum RoleGroup {
    TANK(Set.of(Roles.NORMAL_TANK)),
    ATTACKER(Set.of(Roles.NORMAL_ATTACKER)),
    SUPPORTER(Set.of(Roles.NORMAL_SUPPORTER));

    private final Set<Role> roles;

    RoleGroup(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return new HashSet(roles);
    }

    public Set<RoleGroup> byRole(Role role) {
        Set<RoleGroup> groups = new HashSet<>();
        for (RoleGroup group : values()) {
            if (group.roles.contains(role)) groups.add(group);
        }
        return groups;
    }
}
