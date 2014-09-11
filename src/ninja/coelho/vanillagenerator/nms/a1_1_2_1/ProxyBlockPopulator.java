package ninja.coelho.vanillagenerator.nms.a1_1_2_1;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ninja.coelho.vanillagenerator.util.LongHash;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class ProxyBlockPopulator extends BlockPopulator {

	protected net.minecraft.server.a1_1_2_1.bj nmsGenerator;
	protected net.minecraft.server.a1_1_2_1.dy nmsWorld;
	protected NMSDummyChunkProvider nmsChunkProvider;
	
	private Map<Long, net.minecraft.server.a1_1_2_1.im> nmsChunks = new HashMap<Long, net.minecraft.server.a1_1_2_1.im>();
	
	public void addChunk(net.minecraft.server.a1_1_2_1.im chunk) {
		this.nmsChunks.put(LongHash.toLong(chunk.j, chunk.k), chunk);
	}
	
	@SuppressWarnings("deprecation")
	public void populate(World world, Random random, Chunk chunk) {
		net.minecraft.server.a1_1_2_1.im nmsChunk = this.nmsChunks.remove(LongHash.toLong(chunk.getX(), chunk.getZ()));
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
