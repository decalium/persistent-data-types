# persistent-data-types
Have you ever been dreaming of saving a list of itemstacks to the pdc in just single line? Now that's possible!
        ```java
        
        PersistentDataType<?, List<ItemStack>> ITEM_STACK_LIST = DataTypes.list(DataTypes.ITEM_STACK);
        
        ```
