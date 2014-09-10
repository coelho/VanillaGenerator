package vanillagenerator.nms.a1_2_6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import vanillagenerator.util.ReflectionUtils;

public class ProxyChunkGenerator extends ChunkGenerator {
	
	private net.minecraft.server.a1_2_6.bu nmsGenerator;
	private net.minecraft.server.a1_2_6.eq nmsWorld;
	
	private ProxyBlockPopulator blockPopulator = new ProxyBlockPopulator();
	private Environment envrionment;
	private boolean nms;
	
	public ProxyChunkGenerator(Environment envrionment) {
		this.envrionment = envrionment;
	}
	
	@Override
	public byte[] generate(World world, Random random, int x, int z) {
		if(!ensureNms(world)) {
			return null;
		}

		// generation
		net.minecraft.server.a1_2_6.ju nmsChunk = nmsGenerator.b(x, z);

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
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public boolean ensureNms(World world) {
		if (!nms) {
			try {
				nmsWorld = ReflectionUtils.newInstanceNoInit(NMSProxyWorldServer.class);
				ReflectionUtils.setFinalField(nmsWorld.getClass(), nmsWorld, "world", world);
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "l", new Random());
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "A", new ArrayList());
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "D", new TreeSet());
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "C", new TreeSet());
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "r", new ArrayList());
				NMSDummyChunkProvider nmsChunkProvider = new NMSDummyChunkProvider();
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "G", nmsChunkProvider);
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "u", world.getSeed());
				net.minecraft.server.a1_2_6.il nmsWorldProvider = net.minecraft.server.a1_2_6.il.a(this.envrionment.getId());
				nmsWorldProvider.a(nmsWorld);
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "q", nmsWorldProvider);
				nmsGenerator = nmsWorldProvider.c();
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
