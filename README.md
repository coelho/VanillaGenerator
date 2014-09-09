VanillaGenerator
=============

ChunkGenerator for the Bukkit API that uses NMS for vanilla terrain generation.

### Dependencies ###

* [VanillaNMS](https://github.com/coelho/vanillanms)
  * (optional) Minecraft Server 1.7.10 refactored
* [Bukkit API](http://repo.bukkit.org/content/groups/public/org/bukkit/bukkit/1.7.9-R0.2/bukkit-1.7.9-R0.2.jar)

*//todo use maven or gradle*

### Usage ###

In your glowstone.yml, for example, set the following:
```
worlds:
  world:
    generator: VanillaGenerator:v1_7_10,normal
```
There are two parameters after the generator name. v1_7_10 is the version of Minecraft that we will be using to generate, and normal is the environment type.

When running your server, be sure to include the Minecraft Server in the class path:
```
$ java -cp minecraftServer.jar:glowstone.jar net.glowstone.GlowServer
```
