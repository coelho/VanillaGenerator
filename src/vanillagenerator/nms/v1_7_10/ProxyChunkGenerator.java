package vanillagenerator.nms.v1_7_10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import vanillagenerator.util.BiomeUtils;
import vanillagenerator.util.ReflectionUtils;

public class ProxyChunkGenerator extends ChunkGenerator {

	static {
		net.minecraft.server.v1_7_10.aji.p();
		net.minecraft.server.v1_7_10.adb.l();
	}
	
	private net.minecraft.server.v1_7_10.apu nmsGenerator;
	private net.minecraft.server.v1_7_10.ahb nmsWorld;
	
	private ProxyBlockPopulator blockPopulator = new ProxyBlockPopulator();
	private Environment envrionment;
	private boolean nms;
	
	public ProxyChunkGenerator(Environment envrionment) {
		this.envrionment = envrionment;
	}
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	@Override
	public short[][] generateExtBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
		if (!nms) {
			try {
				nmsWorld = ReflectionUtils.newInstanceNoInit(NMSProxyWorldServer.class);
				ReflectionUtils.setFinalField(nmsWorld.getClass(), nmsWorld, "world", world);
				net.minecraft.server.v1_7_10.ays nmsWorldData = ReflectionUtils.newInstanceNoInit(net.minecraft.server.v1_7_10.ays.class);
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "s", new Random());
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "u", new ArrayList());
				NMSDummyChunkProvider nmsChunkProvider = new NMSDummyChunkProvider();
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "v", nmsChunkProvider);
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "z", ReflectionUtils.newInstanceNoInit(NMSDummyPersistentCollection.class));
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "x", nmsWorldData);
				ReflectionUtils.setFinalField(nmsWorldData.getClass(), nmsWorldData, "a", world.getSeed());
				ReflectionUtils.setFinalField(nmsWorldData.getClass(), nmsWorldData, "b", net.minecraft.server.v1_7_10.ahm.a(world.getWorldType().getName()));
				ReflectionUtils.setFinalField(nmsWorldData.getClass(), nmsWorldData, "t", world.canGenerateStructures());
				net.minecraft.server.v1_7_10.aqo nmsWorldProvider = net.minecraft.server.v1_7_10.aqo.a(this.envrionment.getId());
				nmsWorldProvider.a(nmsWorld);
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "t", nmsWorldProvider);
				nmsGenerator = nmsWorldProvider.c();
				blockPopulator.nmsGenerator = nmsGenerator;
				blockPopulator.nmsWorld = nmsWorld;
				blockPopulator.nmsChunkProvider = nmsChunkProvider;
			} catch(Exception exception) {
				exception.printStackTrace();
				return null;
			}
			nms = true;
		}
		
		// generation
		net.minecraft.server.v1_7_10.apx nmsChunk = nmsGenerator.c(x, z);
		net.minecraft.server.v1_7_10.apz[] nmsChunkSections = nmsChunk.i();
		
		// set biomes
		int cursorX;
		int cursorZ;
		byte[] biomeBytes = nmsChunk.m();
		for (cursorX = 0; cursorX < 16; cursorX++) {
			for(cursorZ = 0; cursorZ < 16; cursorZ++) {
				biomes.setBiome(cursorX, cursorZ, Biome.valueOf(BiomeUtils.translateBiomeName(net.minecraft.server.v1_7_10.ahu.d(biomeBytes[(cursorZ << 4) | cursorX]).af)));
			}
		}
		
		// set result
		int maxHeight = world.getMaxHeight();
		short[][] result = new short[maxHeight / 16][];
		net.minecraft.server.v1_7_10.apz nmsChunkSection;
		for (int i = 0; i < result.length; i++) {
			nmsChunkSection = nmsChunkSections[i];
			if (nmsChunkSection == null) {
				continue;
			}
			result[i] = new short[16 * 16 * 16];
			byte[] idArray = nmsChunkSection.g();
			for (int j = 0; j < idArray.length; j++) {
				result[i][j] = idArray[j];
			}
		}
		
		// block populator
		blockPopulator.addChunk(nmsChunk);

		return result;
	}
	
	public List<BlockPopulator> getDefaultPopulators(World world) {
		return Arrays.asList((BlockPopulator) blockPopulator);
	}
	
}
