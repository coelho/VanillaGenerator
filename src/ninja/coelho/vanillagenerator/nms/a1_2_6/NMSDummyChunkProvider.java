package ninja.coelho.vanillagenerator.nms.a1_2_6;

import net.minecraft.server.a1_2_6.ju;
import net.minecraft.server.a1_2_6.bu;
import net.minecraft.server.a1_2_6.jc;

public class NMSDummyChunkProvider implements bu {

	protected ju chunk;
	
	public boolean b() {
		return false;
	}

	public void a(bu arg0, int arg1, int arg2) {
		
	}

	public int g() {
		return 0;
	}

	public ju b(int x, int y) {
		if(this.chunk == null) {
			return null;
		}
		if (this.chunk.j != x || this.chunk.k != y) {
			return new NMSDummyChunk(this.chunk.d, x, y);
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

	public boolean a(boolean arg0, jc arg1) {
		return false;
	}

	public boolean a() {
		return false;
	}

}
