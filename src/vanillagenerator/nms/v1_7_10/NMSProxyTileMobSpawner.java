package vanillagenerator.nms.v1_7_10;

import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

public class NMSProxyTileMobSpawner extends net.minecraft.server.v1_7_10.apj {

	private CreatureSpawner creatureSpawner;
	
	public class NMSProxyMobSpawner extends net.minecraft.server.v1_7_10.agq {
		
		public net.minecraft.server.v1_7_10.ahb a() {
			return null;
		}

		public void a(int arg0) {
			
		}

		public int b() {
			return 0;
		}

		public int c() {
			return 0;
		}

		public int d() {
			return 0;
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
	public net.minecraft.server.v1_7_10.agq a() {
		return new NMSProxyMobSpawner();
	}
	
}
