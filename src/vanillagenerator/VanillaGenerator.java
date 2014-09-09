package vanillagenerator;

import org.bukkit.World.Environment;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class VanillaGenerator extends JavaPlugin {

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		String[] split = id.split("\\,");
		if (split[0].startsWith("v1_7_10")) {
			return new vanillagenerator.nms.v1_7_10.ProxyChunkGenerator(Environment.valueOf(split[1].toUpperCase()));
		} else if (split[0].startsWith("a1_2_6")) {
			return new vanillagenerator.nms.a1_2_6.ProxyChunkGenerator(Environment.valueOf(split[1].toUpperCase()));
		}
		return null;
	}

}
