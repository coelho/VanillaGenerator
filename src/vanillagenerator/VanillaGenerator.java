package vanillagenerator;

import org.bukkit.World.Environment;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class VanillaGenerator extends JavaPlugin {

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		if (id.startsWith("v1_7_10,")) {
			return new vanillagenerator.nms.v1_7_10.ProxyChunkGenerator(Environment.valueOf(id.substring(8).toUpperCase()));
		}
		return null;
	}

}
