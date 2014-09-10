package vanillagenerator.nms.v1_8;

import net.minecraft.server.v1_8.bec;
import net.minecraft.server.v1_8.dt;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;

public class NMSProxyWorldServer extends net.minecraft.server.v1_8.qt {

	private World world;
	
	public NMSProxyWorldServer() {
		super(null, null, null, 0, null);
	}

	@SuppressWarnings("deprecation")
	@Override
	public net.minecraft.server.v1_8.atr c(dt arg0) {
		return net.minecraft.server.v1_8.atr.c(world.getBlockAt(arg0.n(), arg0.o(), arg0.p()).getTypeId());
	}

	@SuppressWarnings("deprecation")
	@Override
	public bec p(dt arg0) {
		Block block = world.getBlockAt(arg0.n(), arg0.o(), arg0.p());
		return net.minecraft.server.v1_8.atr.c(block.getTypeId()).a(block.getData());
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean a(dt arg0, net.minecraft.server.v1_8.bec nmsBlock, int arg2) {
		Block block = world.getBlockAt(arg0.n(), arg0.o(), arg0.p());
		block.setTypeId(net.minecraft.server.v1_8.atr.a(nmsBlock.c()));
		block.setData((byte) nmsBlock.c().c(nmsBlock));
		return true;
	}

	@Override
	public net.minecraft.server.v1_8.dt m(dt arg0) {
		return new net.minecraft.server.v1_8.dt(arg0.n(), world.getHighestBlockYAt(arg0.n(), arg0.p()), arg0.p());
	}

	@Override
	public net.minecraft.server.v1_8.bcm s(dt arg0) {
		Block block = world.getBlockAt(arg0.n(), arg0.o(), arg0.p());
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
	
	@Override
	public void d(dt arg0, net.minecraft.server.v1_8.atr arg1) {
		// dummy - block updates?
	}

	@Override
	public boolean d(net.minecraft.server.v1_8.wv arg0) {
		// dummy
		return false;
	}

}
