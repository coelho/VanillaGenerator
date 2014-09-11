package ninja.coelho.vanillagenerator.nms.a1_1_2_1;

import net.minecraft.server.a1_1_2_1.hw;
import net.minecraft.server.a1_1_2_1.im;
import net.minecraft.server.a1_1_2_1.bj;

public class NMSDummyChunkProvider implements bj {

	protected im chunk;
	
	public boolean b() {
		return false;
	}

	public void a(bj arg0, int arg1, int arg2) {
		
	}

	public im b(int x, int y) {
		if(this.chunk == null) {
			return null;
		}
		if (this.chunk.j != x || this.chunk.k != y) {
			return new NMSDummyChunk(this.chunk.d, null, x, y);
		}
		return this.chunk;
	}

	public boolean a(int x, int y) {
		if (this.chunk == null) {
			return false;
		}
		if (this.chunk.j != x || this.chunk.k != y) {
			return false;
		}
		return true;
	}

	public boolean a(boolean arg0, hw arg1) {
		return false;
	}

	public boolean a() {
		return false;
	}

}
