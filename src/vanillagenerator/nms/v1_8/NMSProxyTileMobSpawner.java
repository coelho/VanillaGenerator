package vanillagenerator.nms.v1_8;

import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

public class NMSProxyTileMobSpawner extends net.minecraft.server.v1_8.bdg {

	private CreatureSpawner creatureSpawner;
	
	public class NMSProxyMobSpawner extends net.minecraft.server.v1_8.aqi {

		@Override
		public net.minecraft.server.v1_8.aqu a() {
			return null;
		}

		@Override
		public void a(int arg0) {
			
		}

		@Override
		public net.minecraft.server.v1_8.dt b() {
			return null;
		}
	
		@Override
		public void a(String mob) {
			mob = mob.toUpperCase();
			if(mob.equals("CAVESPIDER")) {
				mob = "CAVE_SPIDER";
			}
			creatureSpawner.setSpawnedType(EntityType.valueOf(mob));
		}

	}
	
	public NMSProxyTileMobSpawner(CreatureSpawner creatureSpawner) {
		this.creatureSpawner = creatureSpawner;
	}
	
	@Override
	public net.minecraft.server.v1_8.aqi b() {
		return new NMSProxyMobSpawner();
	}
	
}
