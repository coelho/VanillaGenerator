package vanillagenerator.nms.v17;

import net.minecraft.server.TileEntity;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;

public class NMSProxyWorldServer extends net.minecraft.server.WorldServer {

	private World world;
	
	public NMSProxyWorldServer() {
		super(null, null, null, 0, null, null);
	}

	@SuppressWarnings("deprecation")
	@Override
	public net.minecraft.server.Block getType(int x, int y, int z) {
		return net.minecraft.server.Block.e(world.getBlockAt(x, y, z).getTypeId());
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean setTypeAndData(int x, int y, int z, net.minecraft.server.Block nmsBlock, int meta, int arg5) {
		Block block = world.getBlockAt(x, y, z);
		block.setTypeId(net.minecraft.server.Block.b(nmsBlock));
		block.setData((byte) meta);
		return true;
	}

	@Override
	public int getHighestBlockYAt(int x, int y) {
		return world.getHighestBlockYAt(x, y);
	}

	@Override
	public TileEntity getTileEntity(int x, int y, int z) {
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
