package com.manya.pdc;

import com.manya.key.KeyFactory;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class LocationDataType implements PersistentDataType<PersistentDataContainer, Location> {
    private final Server server;
    private final NamespacedKey worldKey, xKey, yKey, zKey, yawKey, pitchKey;
    public LocationDataType(Plugin plugin) {
        this.server = plugin.getServer();
        KeyFactory keyFactory = KeyFactory.plugin(plugin);
        this.worldKey = keyFactory.create("world");
        this.xKey = keyFactory.create("x");
        this.yKey = keyFactory.create("y");
        this.zKey = keyFactory.create("z");
        this.yawKey = keyFactory.create("yaw");
        this.pitchKey = keyFactory.create("pitch");
    }

    @Override
    public @NotNull Class<PersistentDataContainer> getPrimitiveType() {
        return PersistentDataContainer.class;
    }

    @Override
    public @NotNull Class<Location> getComplexType() {
        return Location.class;
    }

    @Override
    public @NotNull PersistentDataContainer toPrimitive(@NotNull Location complex, @NotNull PersistentDataAdapterContext context) {
        PersistentDataContainer container = context.newPersistentDataContainer();
        if(complex.isWorldLoaded()) container.set(worldKey, DataTypes.UUID, complex.getWorld().getUID());
        container.set(xKey, PersistentDataType.DOUBLE, complex.getX());
        container.set(yKey, PersistentDataType.DOUBLE, complex.getY());
        container.set(zKey, PersistentDataType.DOUBLE, complex.getZ());
        if(complex.getYaw() != 0) container.set(yawKey, PersistentDataType.FLOAT, complex.getYaw());
        if(complex.getPitch() != 0) container.set(pitchKey, PersistentDataType.FLOAT, complex.getPitch());
        return container;
    }

    @Override
    public @NotNull Location fromPrimitive(@NotNull PersistentDataContainer primitive, @NotNull PersistentDataAdapterContext context) {
        UUID worldUuid = primitive.get(worldKey, DataTypes.UUID);
        World world = worldUuid == null ? null : server.getWorld(worldUuid);
        double x = Objects.requireNonNull(primitive.get(xKey, PersistentDataType.DOUBLE), "x cannot be null");
        double y = Objects.requireNonNull(primitive.get(yKey, PersistentDataType.DOUBLE), "y cannot be null");
        double z = Objects.requireNonNull(primitive.get(zKey, PersistentDataType.DOUBLE), "z cannot be null");
        float yaw = primitive.getOrDefault(yawKey, PersistentDataType.FLOAT, (float) 0);
        float pitch = primitive.getOrDefault(pitchKey, PersistentDataType.FLOAT, (float) 0);
        return new Location(world, x, y, z, yaw, pitch);
    }
}
