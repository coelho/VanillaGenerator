package vanillagenerator.nms.a1_2_6;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import vanillagenerator.util.LongHash;

public class ProxyBlockPopulator extends BlockPopulator {

	protected net.minecraft.server.a1_2_6.bu nmsGenerator;
	protected net.minecraft.server.a1_2_6.eq nmsWorld;
	protected NMSDummyChunkProvider nmsChunkProvider;
	
	private Map<Long, net.minecraft.server.a1_2_6.ju> nmsChunks = new HashMap<Long, net.minecraft.server.a1_2_6.ju>();
	
	public void addChunk(net.minecraft.server.a1_2_6.ju chunk) {
		this.nmsChunks.put(LongHash.toLong(chunk.j, chunk.k), chunk);
	}
	
	@SuppressWarnings("deprecation")
	public void populate(World world, Random random, Chunk chunk) {
		net.minecraft.server.a1_2_6.ju nmsChunk = this.nmsChunks.remove(LongHash.toLong(chunk.getX(), chunk.getZ()));
		if (nmsChunk == null) {
			nmsChunk = nmsGenerator.b(chunk.getX(), chunk.getZ());
		}
		int y;
		int x;
		int z;
		int meta;
		for (y = 0; y < 128; y++) {
			for (x = 0; x < 16; x++) {
				for (z = 0; z < 16; z++) {
					meta = nmsChunk.e.a(x, y, z);
					if (meta == 0) {
						continue;
					}
					chunk.getBlock(x, y, z).setData((byte) meta);
				}
			}
		}
		nmsChunkProvider.chunk = nmsChunk;
		nmsGenerator.a(null, chunk.getX(), chunk.getZ());
		((Runnable) nmsWorld).run();
		nmsChunkProvider.chunk = null;
	}
	
}
