package vanillagenerator;

import org.bukkit.World.Environment;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import vanillagenerator.nms.v17.V17ChunkGenerator;

public class VanillaGenerator extends JavaPlugin {

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		if (id.startsWith("v17,")) {
			return new V17ChunkGenerator(Environment.valueOf(id.substring(4).toUpperCase()));
		}
		return null;
	}

}
