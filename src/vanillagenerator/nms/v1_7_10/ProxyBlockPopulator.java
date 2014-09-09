package vanillagenerator.nms.v1_7_10;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import vanillagenerator.util.LongHash;

public class ProxyBlockPopulator extends BlockPopulator {

	protected net.minecraft.server.v1_7_10.apu nmsGenerator;
	protected net.minecraft.server.v1_7_10.ahb nmsWorld;
	protected NMSDummyChunkProvider nmsChunkProvider;
	
	private Map<Long, net.minecraft.server.v1_7_10.apx> nmsChunks = new HashMap<Long, net.minecraft.server.v1_7_10.apx>();
	
	public void addChunk(net.minecraft.server.v1_7_10.apx chunk) {
		this.nmsChunks.put(LongHash.toLong(chunk.g, chunk.h), chunk);
	}
	
	@SuppressWarnings("deprecation")
	public void populate(World world, Random random, Chunk chunk) {
		net.minecraft.server.v1_7_10.apx nmsChunk = this.nmsChunks.remove(LongHash.toLong(chunk.getX(), chunk.getZ()));
		if (nmsChunk == null) {
			nmsChunk = nmsGenerator.c(chunk.getX(), chunk.getZ());
		}
		net.minecraft.server.v1_7_10.apz[] nmsChunkSections = nmsChunk.i();
		net.minecraft.server.v1_7_10.apz nmsChunkSection;
		for (int i = 0; i < nmsChunkSections.length; i++) {
			nmsChunkSection = nmsChunkSections[i];
			if (nmsChunkSection == null) {
				continue;
			}
			int x;
			int y;
			int z;
			int meta;
			for (y = 0; y < 16; y++) {
				for (x = 0; x < 16; x++) {
					for (z = 0; z < 16; z++) {
						meta = nmsChunkSection.b(x, y, z);
						if (meta == 0) {
							continue;
						}
						chunk.getBlock(x, (i * 16) + y, z).setData((byte) meta);
					}
				}
			}
		}
		nmsChunkProvider.chunk = nmsChunk;
		nmsGenerator.a(null, chunk.getX(), chunk.getZ());
		nmsChunkProvider.chunk = null;
	}
	
}
