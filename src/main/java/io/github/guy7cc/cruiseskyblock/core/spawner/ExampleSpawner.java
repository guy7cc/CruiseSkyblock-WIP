package io.github.guy7cc.cruiseskyblock.core.spawner;

import io.github.guy7cc.cruiseskyblock.CruiseSkyblock;
import io.github.guy7cc.cruiseskyblock.core.entity.CustomEntityProperty;
import io.github.guy7cc.cruiseskyblock.core.handler.spawner.CustomSpawnerEntityEventHandler;
import io.github.guy7cc.cruiseskyblock.core.item.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ExampleSpawner extends CustomSpawner {
    public ExampleSpawner(int id) {
        super(id, "example_spawner", 3);
    }

    @Override
    public void spawn(CustomSpawnerState state, CustomSpawnerEntityEventHandler onSpawn) {
        Random random = new Random();
        int num = random.nextInt(1, 4);
        List<Location> locList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Location loc = state.getNextSpawnLocation(EntityType.ZOMBIE);
            if (loc != null) locList.add(loc);
        }
        for (Location location : locList) {
            Entity entity = state.getWorld().spawn(location, Zombie.class);
            onSpawn.accept(state, entity);
        }
    }

    @Override
    public void tick(CustomSpawnerState state, int tick) {
        if (tick % 5 == 0) state.getWorld().spawnParticle(Particle.PORTAL, state.location, 1);
    }

    @Override
    public void onEnabled(CustomSpawnerState state) {
        createEye(state);
    }

    @Override
    public void onPlayerDetected(CustomSpawnerState state) {
        if (!state.location.isWorldLoaded()) return;
        removeEye(state);
        createGoldenEye(state);
    }

    @Override
    public void onPlayerOutOfDetection(CustomSpawnerState state) {
        if (!state.location.isWorldLoaded()) return;
        removeEye(state);
        createEye(state);
    }

    @Override
    public void onEntityKilled(CustomSpawnerState state, Entity entity) {
        super.onEntityKilled(state, entity);
    }

    @Override
    public void onDisabled(CustomSpawnerState state) {
        if (!state.location.isWorldLoaded()) return;
        removeEye(state);
    }

    @Override
    public boolean breakingEffect(CustomSpawnerState state, int tick) {
        if (!state.location.isWorldLoaded()) return true;
        Random random = new Random();
        double r = 0.2 * (1 - Math.exp(-tick / 10d));
        double theta = random.nextDouble(0, Math.PI);
        double phi = random.nextDouble(0, 2 * Math.PI);

        state.getWorld().spawnParticle(Particle.FLAME, state.location, 1, Math.sin(theta) * Math.cos(phi), Math.sin(theta) * Math.sin(phi), Math.cos(theta), r);
        return tick > 40;
    }

    private void createEye(CustomSpawnerState state) {
        Location loc = new Location(state.location.getWorld(), state.location.getX(), state.location.getY() - 0.125d, state.location.getZ());
        Snowball eye = state.getWorld().spawn(loc, Snowball.class);
        eye.setItem(new ItemStack(Material.ENDER_EYE));
        eye.setPersistent(false);
        eye.setGravity(false);
        eye.setInvulnerable(true);
        state.property.addAccessory(eye.getUniqueId());
        CruiseSkyblock.customEntityProperty.set(eye, new CustomEntityProperty(eye));
    }

    private void createGoldenEye(CustomSpawnerState state) {
        Location loc = new Location(state.location.getWorld(), state.location.getX(), state.location.getY() - 0.125d, state.location.getZ());
        Snowball eye = state.getWorld().spawn(loc, Snowball.class);
        eye.setItem(CustomItems.GOLDEN_EYE.give());
        eye.setPersistent(false);
        eye.setGravity(false);
        eye.setInvulnerable(true);
        state.property.addAccessory(eye.getUniqueId());
        CruiseSkyblock.customEntityProperty.set(eye, new CustomEntityProperty(eye));
    }

    private void removeEye(CustomSpawnerState state) {
        for (UUID accessoryUuid : state.property.getAccessories()) {
            Entity eye = Bukkit.getEntity(accessoryUuid);
            if (eye != null) eye.remove();
        }
    }
}
