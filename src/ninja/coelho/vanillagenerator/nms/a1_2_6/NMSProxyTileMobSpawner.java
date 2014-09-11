package ninja.coelho.vanillagenerator.nms.a1_2_6;

import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

public class NMSProxyTileMobSpawner extends net.minecraft.server.a1_2_6.cf implements Runnable {

	private CreatureSpawner creatureSpawner;

	public NMSProxyTileMobSpawner(CreatureSpawner creatureSpawner) {
		this.creatureSpawner = creatureSpawner;
	}
	
	public void run() {
		creatureSpawner.setSpawnedType(EntityType.valueOf(this.f.toUpperCase()));
	}

}
