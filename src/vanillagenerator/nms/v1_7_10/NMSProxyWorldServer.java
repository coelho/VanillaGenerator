package vanillagenerator.nms.v1_7_10;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;

public class NMSProxyWorldServer extends net.minecraft.server.v1_7_10.mt {

	private World world;
	
	public NMSProxyWorldServer() {
		super(null, null, null, 0, null, null);
	}

	@SuppressWarnings("deprecation")
	@Override
	public net.minecraft.server.v1_7_10.aji a(int x, int y, int z) {
		return net.minecraft.server.v1_7_10.aji.e(world.getBlockAt(x, y, z).getTypeId());
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean d(int x, int y, int z, net.minecraft.server.v1_7_10.aji nmsBlock, int meta, int arg5) {
		Block block = world.getBlockAt(x, y, z);
		block.setTypeId(net.minecraft.server.v1_7_10.aji.b(nmsBlock));
		block.setData((byte) meta);
		return true;
	}

	@Override
	public int f(int x, int y) {
		return world.getHighestBlockYAt(x, y);
	}

	@Override
	public net.minecraft.server.v1_7_10.aor o(int x, int y, int z) {
		Block block = world.getBlockAt(x, y, z);
		if (block.getType() == Material.AIR) {
			return null;
		}
		BlockState blockState = block.getState();
		if (blockState == null) {
			return null;
		}
		// todo: we need support for a chest
		if (blockState instanceof CreatureSpawner) {
			return new NMSProxyTileMobSpawner((CreatureSpawner) blockState);
		} else {
			System.out.println("NMSProxyWorldServer missing: " + blockState.getClass().getName());
		}
		return null;
	}
	
}
