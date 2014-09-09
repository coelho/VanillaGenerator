package vanillagenerator.nms.a1_2_6;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.CreatureSpawner;

public class NMSProxyWorldServer extends net.minecraft.server.a1_2_6.ex implements Runnable {

	private World world;
	private List<Runnable> queue;

	public NMSProxyWorldServer() {
		super(null, null, null, 0);
	}

	@Override
	public int b(int x, int y, int z) {
		return this.a(x, y, z);
	}

	@SuppressWarnings("deprecation")
	@Override
	public int a(int x, int y, int z) {
		return world.getBlockAt(x, y, z).getTypeId();
	}

	@Override
	public boolean a(int x, int y, int z, int type) {
		return this.a(x, y, z, type, 0);
	}

	@Override
	public boolean c(int x, int y, int z, int type) {
		return this.a(x, y, z, type, 0);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean a(int x, int y, int z, int type, int meta) {
		Block block = world.getBlockAt(x, y, z);
		block.setTypeId(type);
		block.setData((byte) meta);
		return true;
	}

	@Override
	public net.minecraft.server.a1_2_6.ay k(int x, int y, int z) {
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
			NMSProxyTileMobSpawner mobSpawner = new NMSProxyTileMobSpawner((CreatureSpawner) blockState);
			this.queue(mobSpawner);
			return mobSpawner;
		} else if (blockState instanceof Chest) {
			net.minecraft.server.a1_2_6.ic chest = new net.minecraft.server.a1_2_6.ic();
			chest.a = this;
			return chest;
		} else {
			System.out.println("NMSProxyWorldServer missing: " + blockState.getClass().getName());
		}
		return null;
	}

	@Override
	public int e(int x, int z) {
		int y = 127;
		while (c(x, y, z).c() && y > 0) {
			y--;
		}
		while (y > 0) {
			int type = a(x, y, z);
			if (type == 0 || (!net.minecraft.server.a1_2_6.gc.m[type].bs.c() && !net.minecraft.server.a1_2_6.gc.m[type].bs.d())) {
				y--;
			} else {
				return y + 1;
			}
		}
		return -1;
	}

	@Override
	public int d(int x, int z) {
		if(!world.isChunkLoaded(x >> 4, z >> 4)) {
			return 0;
		}
		return world.getHighestBlockYAt(x, z);
	}
	
	@Override
	public boolean g(int x, int y, int z) {
		return y >= world.getHighestBlockYAt(x, z);
	}
	
	@Override
	public int a(int x, int y, int z, boolean arg3) {
		// dummy - block updates?
		return 0;
	}
	
	@Override
	public int a(net.minecraft.server.a1_2_6.dn paramdn, int x, int y, int z) {
		// dummy - seems to have something to do with lighting
		return 0;
	}
	
	@Override
	public void a(net.minecraft.server.a1_2_6.dn paramdn, int x, int y, int z, int arg4) {
		// dummy - seems to have something to do with lighting
	}
	
	@Override
	public boolean a(net.minecraft.server.a1_2_6.ea paramea) {
		// dummy
		return false;
	}

	@Override
	public void b(int arg0, int arg1, int arg2, net.minecraft.server.a1_2_6.ay arg3) {
		System.out.println("NMSProxyWorldServer missing: " + arg3);
	}

	public void queue(Runnable runnable) {
		if (this.queue == null) {
			this.queue = new LinkedList<Runnable>();
		}
		this.queue.add(runnable);
	}

	public void run() {
		if (this.queue == null) {
			return;
		}
		for(Runnable runnable : this.queue) {
			runnable.run();
		}
		this.queue.clear();
	}

}
