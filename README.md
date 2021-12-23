# Persistent data types
Have you ever been dreaming of saving a list of itemstacks to the pdc in just single line? Now that's possible!
```java
PersistentDataType<?, List<ItemStack>> ITEM_STACK_LIST = DataTypes.list(DataTypes.ITEM_STACK);
```

##Features
- A lots of useful data types for gson, uuids, time, items and entities and such.
- A powerful collection data type framework. In single line, you can pick the most compact, beautiful and 
efficient implementation to store multiple elements at the same time.
- Fast and lightweight serialization algorithms. Overhead is avoided at all cost.

##Where can i find the list of data types?
Honestly, i'm too lazy to sync docs with code. To see existing data types, jump at DataTypes class. 
All methods and constants are documented and self-explanatory.


##Okay, but how do i use them?

Well, it is fine to use this library in 2 ways.
Artifacts are posted on my repository.

###Adding repository

####gradle
```kotlin
maven {
    url = uri("https://repo.decalium.ru/releases")
}
```
####maven
```xml
<repository>
  <id>decalium-repository-releases</id>
  <name>Decalium Network Repository</name>
  <url>https://repo.decalium.ru/releases</url>
</repository>
```

And then, add the artifact:

####gradle
```kotlin
implementation("com.manya:persistent-data-types:1.0.1")
```

####maven
```xml
<dependency>
  <groupId>com.manya</groupId>
  <artifactId>persistent-data-types</artifactId>
  <version>1.0.1</version>
</dependency>
```

Alternatively, you can just copy classes you need to the project.













