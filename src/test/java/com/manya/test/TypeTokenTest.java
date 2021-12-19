package com.manya.test;



import com.manya.key.KeyFactory;
import com.manya.pdc.DataTypes;
import com.manya.pdc.base.UuidDataType;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class TypeTokenTest {

    public static void main(String[] args) {

        NamespacedKey key = KeyFactory.DECALIUM.create("test");

        FakePersistentDataContainer container = new FakePersistentDataContainer();

        UUID uuid = UUID.randomUUID();
        PersistentDataType<?, List<Component>> COMPONENT_LIST = DataTypes.list(DataTypes.COMPONENT);
        ArrayList<Component> uuids = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            uuids.add(Component.text(RandomStringUtils.random(4)));
        }
        container.set(key, COMPONENT_LIST, uuids);

        List<Component> uuids2 = container.get(key, COMPONENT_LIST);



        System.out.println(container);


    }



}
