package vanillagenerator.nms.v17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import vanillagenerator.util.ReflectionUtils;

public class V17ChunkGenerator extends ChunkGenerator {

	static {
		net.minecraft.server.Block.p();
		net.minecraft.server.Item.l();
	}
	
	private net.minecraft.server.IChunkProvider nmsGenerator;
	private net.minecraft.server.World nmsWorld;
	
	private MetaBlockPopulator blockPopulator = new MetaBlockPopulator();
	private Environment envrionment;
	private boolean nms;
	
	public V17ChunkGenerator(Environment envrionment) {
		this.envrionment = envrionment;
	}
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	@Override
	public short[][] generateExtBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
		if (!nms) {
			try {
				nmsWorld = ReflectionUtils.newInstanceNoInit(NMSProxyWorldServer.class);
				ReflectionUtils.setFinalField(nmsWorld.getClass(), nmsWorld, "world", world);
				net.minecraft.server.WorldData nmsWorldData = ReflectionUtils.newInstanceNoInit(net.minecraft.server.WorldData.class);
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "random", new Random());
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "u", new ArrayList());
				NMSDummyChunkProvider nmsChunkProvider = new NMSDummyChunkProvider();
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "chunkProvider", nmsChunkProvider);
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "worldMaps", ReflectionUtils.newInstanceNoInit(NMSDummyPersistentCollection.class));
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "worldData", nmsWorldData);
				ReflectionUtils.setFinalField(nmsWorldData.getClass(), nmsWorldData, "seed", world.getSeed());
				ReflectionUtils.setFinalField(nmsWorldData.getClass(), nmsWorldData, "type", net.minecraft.server.WorldType.getType(world.getWorldType().getName()));
				net.minecraft.server.WorldProvider nmsWorldProvider = net.minecraft.server.WorldProvider.byDimension(this.envrionment.getId());
				nmsWorldProvider.a(nmsWorld);
				ReflectionUtils.setFinalField(nmsWorld.getClass().getSuperclass().getSuperclass(), nmsWorld, "worldProvider", nmsWorldProvider);
				nmsGenerator = nmsWorldProvider.getChunkProvider();
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
		net.minecraft.server.Chunk nmsChunk = nmsGenerator.getChunkAt(x, z);
		net.minecraft.server.ChunkSection[] nmsChunkSections = nmsChunk.i();
		
		// set biomes
		int cursorX;
		int cursorZ;
		byte[] biomeBytes = nmsChunk.m();
		for (cursorX = 0; cursorX < 16; cursorX++) {
			for(cursorZ = 0; cursorZ < 16; cursorZ++) {
				biomes.setBiome(cursorX, cursorZ, Biome.valueOf(translateBiomeName(net.minecraft.server.BiomeBase.getBiome(biomeBytes[(cursorZ << 4) | cursorX]).af)));
			}
		}
		
		// set result
		int maxHeight = world.getMaxHeight();
		short[][] result = new short[maxHeight / 16][];
		net.minecraft.server.ChunkSection nmsChunkSection;
		for (int i = 0; i < result.length; i++) {
			nmsChunkSection = nmsChunkSections[i];
			if (nmsChunkSection == null) {
				continue;
			}
			result[i] = new short[16 * 16 * 16];
			byte[] idArray = nmsChunkSection.getIdArray();
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

	private static final String translateBiomeName(String input) {
		if (input.endsWith(" F")) {
			input = input.substring(0, input.length() - 1) + "Forest";
		}
		if (input.endsWith(" M")) {
			input = input.substring(0, input.length() - 1) + "Mountains";
		}
		if (input.contains("+")) {
			input = input.replace("+", " Plus");
		}
		if (input.contains(" ")) {
			return input.toUpperCase().replace(' ', '_');
		}
		StringBuilder builder = new StringBuilder();
		char[] chars = input.toCharArray();
		for(int i = 0; i < chars.length; i++) {
			if (i != 0 && Character.isUpperCase(chars[i])) {
				builder.append("_");
				builder.append(chars[i]);
				continue;
			}
			builder.append(Character.toUpperCase(chars[i]));
		}
		return builder.toString();
	}
	
}
