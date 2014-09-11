package ninja.coelho.vanillagenerator.nms.v1_8;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ninja.coelho.vanillagenerator.util.LongHash;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class ProxyBlockPopulator extends BlockPopulator {

	protected net.minecraft.server.v1_8.bfe nmsGenerator;
	protected net.minecraft.server.v1_8.aqu nmsWorld;
	protected NMSDummyChunkProvider nmsChunkProvider;
	
	private Map<Long, net.minecraft.server.v1_8.bfh> nmsChunks = new HashMap<Long, net.minecraft.server.v1_8.bfh>();
	
	public void addChunk(net.minecraft.server.v1_8.bfh chunk) {
		this.nmsChunks.put(LongHash.toLong(chunk.a, chunk.b), chunk);
	}
	
	@SuppressWarnings("deprecation")
	public void populate(World world, Random random, Chunk chunk) {
		net.minecraft.server.v1_8.bfh nmsChunk = this.nmsChunks.remove(LongHash.toLong(chunk.getX(), chunk.getZ()));
		if (nmsChunk == null) {
			nmsChunk = nmsGenerator.d(chunk.getX(), chunk.getZ());
		}
		net.minecraft.server.v1_8.bfm[] nmsChunkSections = nmsChunk.h();
		net.minecraft.server.v1_8.bfm nmsChunkSection;
		for (int i = 0; i < nmsChunkSections.length; i++) {
			nmsChunkSection = nmsChunkSections[i];
			if (nmsChunkSection == null) {
				continue;
			}
			int x;
			int y;
			int z;
			int meta;
			char[] ids = nmsChunkSection.g();
			for (y = 0; y < 16; y++) {
				for (x = 0; x < 16; x++) {
					for (z = 0; z < 16; z++) {
						meta = ids[(y << 8 | z << 4 | x)] & 0xF;
						if (meta == 0) {
							continue;
						}
						chunk.getBlock(x, (i * 16) + y, z).setData((byte) meta);
					}
				}
			}
		}
		nmsChunkProvider.chunk = nmsChunk;
		nmsGenerator.a((net.minecraft.server.v1_8.bfe) null, chunk.getX(), chunk.getZ());
		nmsChunkProvider.chunk = null;
	}
	
}
