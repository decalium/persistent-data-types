package com.manya.pdc.minecraft;

import com.google.common.base.MoreObjects;
import com.manya.key.KeyFactory;
import com.manya.pdc.DataTypes;
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

import static java.util.Objects.*;

public class LocationDataType implements PersistentDataType<PersistentDataContainer, Location> {
    private static final float ZERO = 0;
    private final Server server;
    private final NamespacedKey worldKey, xKey, yKey, zKey, yawKey, pitchKey;
    public LocationDataType(@NotNull Server server, @NotNull KeyFactory keyFactory) {
        requireNonNull(keyFactory, "keyFactory cannot be null");
        this.server = requireNonNull(server, "server cannot be null");
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

        if(complex.getYaw() != ZERO) container.set(yawKey, PersistentDataType.FLOAT, complex.getYaw());
        if(complex.getPitch() != ZERO) container.set(pitchKey, PersistentDataType.FLOAT, complex.getPitch());

        return container;
    }

    @Override
    public @NotNull Location fromPrimitive(@NotNull PersistentDataContainer primitive, @NotNull PersistentDataAdapterContext context) {
        UUID worldUuid = primitive.get(worldKey, DataTypes.UUID);
        World world = worldUuid == null ? null : server.getWorld(worldUuid);

        double x = requireNonNull(primitive.get(xKey, PersistentDataType.DOUBLE), "x cannot be null");
        double y = requireNonNull(primitive.get(yKey, PersistentDataType.DOUBLE), "y cannot be null");
        double z = requireNonNull(primitive.get(zKey, PersistentDataType.DOUBLE), "z cannot be null");

        float yaw = primitive.getOrDefault(yawKey, PersistentDataType.FLOAT, ZERO);
        float pitch = primitive.getOrDefault(pitchKey, PersistentDataType.FLOAT, ZERO);

        return new Location(world, x, y, z, yaw, pitch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(server, worldKey, xKey, yKey, zKey, yawKey, pitchKey);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationDataType that = (LocationDataType) o;
        return server.equals(that.server) &&
                worldKey.equals(that.worldKey) &&
                xKey.equals(that.xKey) &&
                yKey.equals(that.yKey) &&
                zKey.equals(that.zKey) &&
                yawKey.equals(that.yawKey) &&
                pitchKey.equals(that.pitchKey);
    }



    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("server", server)
                .add("worldKey", worldKey)
                .add("xKey", xKey)
                .add("yKey", yKey)
                .add("zKey", zKey)
                .add("yawKey", yawKey)
                .add("pitchKey", pitchKey)
                .toString();
    }
}
