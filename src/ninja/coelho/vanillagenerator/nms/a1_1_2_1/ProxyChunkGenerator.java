package ninja.coelho.vanillagenerator.nms.a1_1_2_1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import ninja.coelho.vanillagenerator.util.ReflectionUtils;

import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class ProxyChunkGenerator extends ChunkGenerator {
	
	private net.minecraft.server.a1_1_2_1.bj nmsGenerator;
	private net.minecraft.server.a1_1_2_1.dy nmsWorld;
	
	private ProxyBlockPopulator blockPopulator = new ProxyBlockPopulator();
	private boolean nms;
	
	@Override
	public byte[] generate(World world, Random random, int x, int z) {
		if(!ensureNms(world)) {
			return null;
		}

		// generation
		net.minecraft.server.a1_1_2_1.im nmsChunk = nmsGenerator.b(x, z);

		// block populator
		blockPopulator.addChunk(nmsChunk);

		return nmsChunk.b;
	}

	public List<BlockPopulator> getDefaultPopulators(World world) {
		if(!ensureNms(world)) {
			return null;
		}
		return Arrays.asList((BlockPopulator) blockPopulator);
	}
	
	@SuppressWarnings({ "rawtypes" })
	private boolean ensureNms(World world) {
		if (!nms) {
			try {
				nmsWorld = ReflectionUtils.newInstanceNoInit(NMSProxyWorldServer.class);
				ReflectionUtils.setFinalField(nmsWorld.getClass(), nmsWorld, "world", world);
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "m", new Random());
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "y", new ArrayList());
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "A", new TreeSet());
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "B", new TreeSet());
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "r", new ArrayList());
				NMSDummyChunkProvider nmsChunkProvider = new NMSDummyChunkProvider();
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "G", nmsChunkProvider);
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "t", world.getSeed());
				nmsGenerator = new net.minecraft.server.a1_1_2_1.hy(nmsWorld, world.getSeed());
				blockPopulator.nmsGenerator = nmsGenerator;
				blockPopulator.nmsWorld = nmsWorld;
				blockPopulator.nmsChunkProvider = nmsChunkProvider;
			} catch(Exception exception) {
				exception.printStackTrace();
				return false;
			}
			nms = true;
		}
		return true;
	}
	
}
