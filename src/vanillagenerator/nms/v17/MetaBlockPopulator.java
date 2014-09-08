package vanillagenerator.nms.v17;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import vanillagenerator.util.LongHash;

public class MetaBlockPopulator extends BlockPopulator {

	protected net.minecraft.server.IChunkProvider nmsGenerator;
	protected net.minecraft.server.World nmsWorld;
	protected NMSDummyChunkProvider nmsChunkProvider;
	
	private Map<Long, net.minecraft.server.Chunk> nmsChunks = new HashMap<Long, net.minecraft.server.Chunk>();
	
	public void addChunk(net.minecraft.server.Chunk chunk) {
		this.nmsChunks.put(LongHash.toLong(chunk.locX, chunk.locZ), chunk);
	}
	
	@SuppressWarnings("deprecation")
	public void populate(World world, Random random, Chunk chunk) {
		net.minecraft.server.Chunk nmsChunk = this.nmsChunks.remove(LongHash.toLong(chunk.getX(), chunk.getZ()));
		if (nmsChunk == null) {
			return;
		}
		net.minecraft.server.ChunkSection[] nmsChunkSections = nmsChunk.i();
		net.minecraft.server.ChunkSection nmsChunkSection;
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
						meta = nmsChunkSection.getData(x, y, z);
						if (meta == 0) {
							continue;
						}
						chunk.getBlock(x, (i * 16) + y, z).setData((byte) meta);
					}
				}
			}
		}
		nmsChunkProvider.chunk = nmsChunk;
		nmsGenerator.getChunkAt(null, chunk.getX(), chunk.getZ());
		nmsChunkProvider.chunk = null;
	}
	
}
