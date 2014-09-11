package ninja.coelho.vanillagenerator.nms.v1_8;

import java.util.List;

import net.minecraft.server.v1_8.aqu;
import net.minecraft.server.v1_8.bfe;
import net.minecraft.server.v1_8.bfh;
import net.minecraft.server.v1_8.dt;
import net.minecraft.server.v1_8.uy;
import net.minecraft.server.v1_8.xp;

public class NMSDummyChunkProvider implements bfe {

	protected net.minecraft.server.v1_8.bfh chunk;

	public bfh a(dt arg0) {
		return this.d(arg0.n() >> 4, arg0.p() >> 4);
	}

	public boolean a(int x, int y) {
		if (this.chunk == null) {
			return false;
		}
		if (this.chunk.a != x || this.chunk.b != y) {
			return false;
		}
		return true;
	}
	
	public bfh d(int x, int y) {
		if (this.chunk == null) {
			return null;
		}
		if (this.chunk.a != x || this.chunk.b != y) {
			return new net.minecraft.server.v1_8.bfg(this.chunk.p(), x, y);
		}
		return this.chunk;
	}

	public boolean a(boolean arg0, uy arg1) {
		// dummy
		return false;
	}

	@SuppressWarnings("rawtypes")
	public List a(xp arg0, dt arg1) {
		// dummy
		return null;
	}

	public void a(bfe arg0, int arg1, int arg2) {
		// dummy
	}

	public dt a(aqu arg0, String arg1, dt arg2) {
		// dummy
		return null;
	}

	public void a(bfh arg0, int arg1, int arg2) {
		// dummy
	}

	public boolean a(bfe arg0, bfh arg1, int arg2, int arg3) {
		// dummy
		return false;
	}

	public void c() {
		// dummy
	}

	public boolean d() {
		// dummy
		return false;
	}

	public boolean e() {
		// dummy
		return false;
	}

	public String f() {
		// dummy
		return null;
	}

	public int g() {
		// dummy
		return 0;
	}

}
