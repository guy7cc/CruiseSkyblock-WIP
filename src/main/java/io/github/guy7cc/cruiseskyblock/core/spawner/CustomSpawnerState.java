package io.github.guy7cc.cruiseskyblock.core.spawner;

import io.github.guy7cc.cruiseskyblock.CruiseSkyblock;
import io.github.guy7cc.cruiseskyblock.core.system.ConditionalTickable;
import io.github.guy7cc.cruiseskyblock.core.entity.CustomEntityProperty;
import io.github.guy7cc.cruiseskyblock.core.handler.spawner.CustomSpawnerEntityEventHandler;
import io.github.guy7cc.cruiseskyblock.core.handler.spawner.CustomSpawnerEventHandler;
import io.github.guy7cc.cruiseskyblock.event.EventList;
import io.github.guy7cc.cruiseskyblock.io.IConfigSerializable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.*;

public class CustomSpawnerState implements ConditionalTickable, IConfigSerializable {
    public final CustomSpawner spawner;
    public final UUID uuid;
    public final Location location;
    private final Set<UUID> spawnedEntitySet = new HashSet<>();
    private boolean playerDetected = false;
    private int killedEntityCount = 0;
    private int nextSpawnTick = 0;
    private int spawnIndex = 0;
    private int tickStartedToBreak = -1;
    private boolean broken = false;
    public final CustomSpawnerProperty property;

    private final List<CustomSpawnerEventHandler> _onEnabled = new ArrayList<>();
    private final List<CustomSpawnerEventHandler> _onPlayerDetected = new ArrayList<>();
    private final List<CustomSpawnerEventHandler> _onPlayerOutOfDetection = new ArrayList<>();
    private final List<CustomSpawnerEntityEventHandler> _onEntitySpawned = new ArrayList<>();
    private final List<CustomSpawnerEntityEventHandler> _onEntityKilled = new ArrayList<>();
    private final List<CustomSpawnerEventHandler> _onDisabled = new ArrayList<>();
    public final EventList<CustomSpawnerEventHandler> onEnabled = new EventList<>(_onEnabled);
    public final EventList<CustomSpawnerEventHandler> onPlayerDetected = new EventList<>(_onPlayerDetected);
    public final EventList<CustomSpawnerEventHandler> onPlayerOutOfDetection = new EventList<>(_onPlayerOutOfDetection);
    public final EventList<CustomSpawnerEntityEventHandler> onEntitySpawned = new EventList<>(_onEntitySpawned);
    public final EventList<CustomSpawnerEntityEventHandler> onEntityKilled = new EventList<>(_onEntityKilled);
    public final EventList<CustomSpawnerEventHandler> onDisabled = new EventList<>(_onDisabled);

    public CustomSpawnerState(CustomSpawner spawner, Location location) {
        this.spawner = spawner;
        this.uuid = UUID.randomUUID();
        this.location = location;
        this.property = new CustomSpawnerProperty(this);
    }

    @Override
    public void tick(int tick) {
        if (!location.isWorldLoaded()) return;
        if (tick % 5 == 0) {
            checkPlayerDetected();
        }
        if (spawnIndex < spawner.spawnCount) {
            spawner.tick(this, tick);
        } else {
            if (spawner.breakingEffect(this, tick - tickStartedToBreak)) broken = true;
        }
    }

    public void processSpawn(int tick) {
        if (tick < nextSpawnTick) return;
        if (spawnIndex >= spawner.spawnCount) {
            return;
        }
        if (!location.isWorldLoaded()) return;
        if (!playerDetected) {
            delay(tick);
            return;
        }
        if (!hasSpawnedEntity()) {
            if (!spawnedEntitySet.isEmpty() && spawnedEntitySet.size() <= killedEntityCount) spawnIndex++;
            if (spawnIndex >= spawner.spawnCount) tickStartedToBreak = tick;
            else {
                spawnedEntitySet.clear();
                spawner.spawn(this, CustomSpawnerState::onEntitySpawned);
            }
        }
        delay(tick);
    }

    public boolean checkPlayerDetected() {
        for (Player player : CruiseSkyblock.plugin.getServer().getOnlinePlayers()) {
            if (player.getLocation().getWorld() == location.getWorld() && player.getLocation().distance(location) <= spawner.detectRange) {
                if (!playerDetected) onPlayerDetected();
                playerDetected = true;
                return true;
            }
        }
        if (playerDetected) onPlayerOutOfDetection();
        playerDetected = false;
        return false;
    }

    public boolean checkPlayerCanSee() {
        for (Player player : CruiseSkyblock.plugin.getServer().getOnlinePlayers()) {
            if (player.getLocation().getWorld() == location.getWorld() && player.getLocation().distance(location) <= spawner.renderRange)
                return true;
        }
        return false;
    }

    public boolean hasSpawnedEntity() {
        if (!location.isWorldLoaded()) return false;
        for (UUID uuid : spawnedEntitySet) {
            Entity entity = Bukkit.getEntity(uuid);
            if (entity != null) return true;
        }
        return false;
    }

    public int getSpawnIndex() {
        return spawnIndex;
    }

    public Location getLocation() {
        return new Location(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public World getWorld() {
        return location.getWorld();
    }

    @Nullable
    public Location getNextSpawnLocation(EntityType type) {
        if (!location.isWorldLoaded()) return null;
        World world = location.getWorld();
        Random rand = new Random();

        CompoundTag entityTag = new CompoundTag();
        entityTag.putString("id", type.getName());
        Optional<net.minecraft.world.entity.EntityType<?>> rawType = net.minecraft.world.entity.EntityType.by(entityTag);
        if (rawType.isEmpty()) return null;

        double x, y, z;
        for (int i = 0; i < 8; i++) {
            x = location.getX() + (spawner.spawnBox.getMaxX() - spawner.spawnBox.getMinX()) * rand.nextDouble() + spawner.spawnBox.getMinX();
            y = location.getY() + (spawner.spawnBox.getMaxY() - spawner.spawnBox.getMinY()) * rand.nextDouble() + spawner.spawnBox.getMinY();
            z = location.getZ() + (spawner.spawnBox.getMaxZ() - spawner.spawnBox.getMinZ()) * rand.nextDouble() + spawner.spawnBox.getMinZ();
            ServerLevel serverLevel = ((CraftWorld) world).getHandle();
            if (serverLevel.noCollision(rawType.get().getAABB(x, y, z))) {
                return new Location(world, x, y, z);
            }
        }
        return null;
    }

    protected void delay(int tick) {
        Random random = new Random();
        nextSpawnTick = tick + (int) ((spawner.maxDelay - spawner.minDelay) * random.nextDouble()) + spawner.minDelay;
    }

    public boolean isBroken() {
        return broken;
    }

    public void onEnabled() {
        spawner.onEnabled(this);
        _onEnabled.forEach(handler -> handler.accept(this));
    }

    public void onPlayerDetected() {
        spawner.onPlayerDetected(this);
        _onPlayerDetected.forEach(handler -> handler.accept(this));
    }

    public void onPlayerOutOfDetection() {
        spawner.onPlayerOutOfDetection(this);
        _onPlayerOutOfDetection.forEach(handler -> handler.accept(this));
    }

    /**
     * This needs to be called on {@link CustomSpawner#spawn} for each entity to spawn
     */
    public void onEntitySpawned(Entity entity) {
        CustomEntityProperty entityProperty = new CustomEntityProperty(entity);
        entityProperty.setSpawnerUUID(this);
        CruiseSkyblock.customEntityProperty.set(entity, entityProperty);
        spawnedEntitySet.add(entity.getUniqueId());
        _onEntitySpawned.forEach(handler -> handler.accept(this, entity));
    }

    public void onEntityKilled(Entity entity) {
        killedEntityCount++;
        spawner.onEntityKilled(this, entity);
        _onEntityKilled.forEach(handler -> handler.accept(this, entity));
    }

    public void onDisabled() {
        spawner.onDisabled(this);
        _onDisabled.forEach(handler -> handler.accept(this));
    }

    @Override
    public void serialize(ConfigurationSection section) {

    }

    @Override
    public void deserialize(ConfigurationSection section) {

    }

    @Override
    public boolean shouldGoToIndex() {
        return checkPlayerCanSee();
    }

    @Override
    public boolean shouldGoToQueue() {
        return !playerDetected;
    }

    @Override
    public boolean shouldBeRemoved() {
        return isBroken();
    }
}
