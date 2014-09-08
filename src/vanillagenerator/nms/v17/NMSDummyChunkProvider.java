package vanillagenerator.nms.v17;

import java.util.List;

import net.minecraft.server.Chunk;
import net.minecraft.server.ChunkPosition;
import net.minecraft.server.EnumCreatureType;
import net.minecraft.server.IChunkProvider;
import net.minecraft.server.IProgressUpdate;
import net.minecraft.server.World;

public class NMSDummyChunkProvider implements IChunkProvider {

	protected net.minecraft.server.Chunk chunk;
	
	public void c() {
		
	}

	public boolean canSave() {
		return false;
	}

	public ChunkPosition findNearestMapFeature(World arg0, String arg1, int arg2, int arg3, int arg4) {
		return null;
	}

	public Chunk getChunkAt(int x, int y) {
		return this.getOrCreateChunk(x, y);
	}

	public void getChunkAt(IChunkProvider arg0, int arg1, int arg2) {
		
	}

	public int getLoadedChunks() {
		return 0;
	}

	@SuppressWarnings("rawtypes")
	public List getMobsFor(EnumCreatureType arg0, int arg1, int arg2, int arg3) {
		return null;
	}

	public String getName() {
		return null;
	}

	public Chunk getOrCreateChunk(int x, int y) {
		if(this.chunk == null) {
			return null;
		}
		if (this.chunk.locX != x || this.chunk.locZ != y) {
			return new net.minecraft.server.EmptyChunk(this.chunk.world, x, y);
		}
		return this.chunk;
	}

	public boolean isChunkLoaded(int arg0, int arg1) {
		return false;
	}

	public void recreateStructures(int arg0, int arg1) {
		
	}

	public boolean saveChunks(boolean arg0, IProgressUpdate arg1) {
		return false;
	}

	public boolean unloadChunks() {
		return false;
	}

}
