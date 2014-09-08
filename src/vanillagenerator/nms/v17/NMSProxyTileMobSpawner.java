package vanillagenerator.nms.v17;

import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

public class NMSProxyTileMobSpawner extends net.minecraft.server.TileEntityMobSpawner {

	private CreatureSpawner creatureSpawner;
	
	public class NMSProxyMobSpawner extends net.minecraft.server.MobSpawnerAbstract {
		
		public net.minecraft.server.World a() {
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
		
		public void a(net.minecraft.server.TileEntityMobSpawnerData paramTileEntityMobSpawnerData) {
			
		}
		
		@Override
		public void a(String mob) {
			creatureSpawner.setSpawnedType(EntityType.valueOf(mob.toUpperCase()));
		}

	}
	
	public NMSProxyTileMobSpawner(CreatureSpawner creatureSpawner) {
		this.creatureSpawner = creatureSpawner;
	}
	
	@Override
	public net.minecraft.server.MobSpawnerAbstract a() {
		return new NMSProxyMobSpawner();
	}
	
}
