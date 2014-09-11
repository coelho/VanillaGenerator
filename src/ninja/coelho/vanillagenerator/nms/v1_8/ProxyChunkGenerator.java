package ninja.coelho.vanillagenerator.nms.v1_8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import ninja.coelho.vanillagenerator.util.BiomeUtils;
import ninja.coelho.vanillagenerator.util.ReflectionUtils;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class ProxyChunkGenerator extends ChunkGenerator {

	static {
		try {
			ReflectionUtils.setFinalField(net.minecraft.server.v1_8.od.class, null, "b", true);
		} catch(Exception exception) {
			exception.printStackTrace();
		}
		net.minecraft.server.v1_8.atr.R();
		net.minecraft.server.v1_8.alq.t();
	}

	private net.minecraft.server.v1_8.bfe nmsGenerator;
	private net.minecraft.server.v1_8.aqu nmsWorld;

	private ProxyBlockPopulator blockPopulator = new ProxyBlockPopulator();
	private Environment envrionment;
	private boolean nms;

	public ProxyChunkGenerator(Environment envrionment) {
		this.envrionment = envrionment;
	}

	@Override
	public short[][] generateExtBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
		if(!ensureNms(world)) {
			return null;
		}

		// generation
		net.minecraft.server.v1_8.bfh nmsChunk = nmsGenerator.d(x, z);
		net.minecraft.server.v1_8.bfm[] nmsChunkSections = nmsChunk.h();

		// set biomes
		int cursorX;
		int cursorZ;
		byte[] biomeBytes = nmsChunk.k();
		for (cursorX = 0; cursorX < 16; cursorX++) {
			for(cursorZ = 0; cursorZ < 16; cursorZ++) {
				biomes.setBiome(cursorX, cursorZ, Biome.valueOf(BiomeUtils.translateBiomeName(net.minecraft.server.v1_8.arm.e(biomeBytes[(cursorZ << 4) | cursorX]).ah)));
			}
		}

		// set result
		int maxHeight = world.getMaxHeight();
		short[][] result = new short[maxHeight / 16][];
		net.minecraft.server.v1_8.bfm nmsChunkSection;
		for (int i = 0; i < result.length; i++) {
			nmsChunkSection = nmsChunkSections[i];
			if (nmsChunkSection == null) {
				continue;
			}
			result[i] = new short[16 * 16 * 16];
			char[] idArray = nmsChunkSection.g();
			for (int j = 0; j < idArray.length; j++) {
				result[i][j] = (byte) ((idArray[j] >> 4) & 0xFF);
			}
		}

		// block populator
		blockPopulator.addChunk(nmsChunk);

		return result;
	}

	public List<BlockPopulator> getDefaultPopulators(World world) {
		if(!ensureNms(world)) {
			return null;
		}
		return Arrays.asList((BlockPopulator) blockPopulator);
	}
	
	@SuppressWarnings({ "deprecation", "rawtypes" })
	private boolean ensureNms(World world) {
		if (!nms) {
			try {
				nmsWorld = ReflectionUtils.newInstanceNoInit(NMSProxyWorldServer.class);
				ReflectionUtils.setFinalField(nmsWorld.getClass(), nmsWorld, "world", world);
				net.minecraft.server.v1_8.bqo nmsWorldData = ReflectionUtils.newInstanceNoInit(net.minecraft.server.v1_8.bqo.class);
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "s", new Random());
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "u", new ArrayList());
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "M", new net.minecraft.server.v1_8.bfb());
				NMSDummyChunkProvider nmsChunkProvider = new NMSDummyChunkProvider();
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "v", nmsChunkProvider);
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "z", ReflectionUtils.newInstanceNoInit(NMSDummyPersistentCollection.class));
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "x", nmsWorldData);
				ReflectionUtils.setFinalField(nmsWorldData.getClass(), nmsWorldData, "b", world.getSeed());
				ReflectionUtils.setFinalField(nmsWorldData.getClass(), nmsWorldData, "c", net.minecraft.server.v1_8.are.a(world.getWorldType().getName()));
				ReflectionUtils.setFinalField(nmsWorldData.getClass(), nmsWorldData, "d", ""); // todo: generator options
				ReflectionUtils.setFinalField(nmsWorldData.getClass(), nmsWorldData, "z", net.minecraft.server.v1_8.vt.a(world.getDifficulty().getValue()));
				ReflectionUtils.setFinalField(nmsWorldData.getClass(), nmsWorldData, "v", world.canGenerateStructures());
				net.minecraft.server.v1_8.bgd nmsWorldProvider = net.minecraft.server.v1_8.bgd.a(this.envrionment.getId());
				nmsWorldProvider.a(nmsWorld);
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "t", nmsWorldProvider);
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
