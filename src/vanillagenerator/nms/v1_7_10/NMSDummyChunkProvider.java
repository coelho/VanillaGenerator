package vanillagenerator.nms.v1_7_10;

import java.util.List;

import net.minecraft.server.v1_7_10.apx;
import net.minecraft.server.v1_7_10.agt;
import net.minecraft.server.v1_7_10.sx;
import net.minecraft.server.v1_7_10.apu;
import net.minecraft.server.v1_7_10.qk;
import net.minecraft.server.v1_7_10.ahb;

public class NMSDummyChunkProvider implements apu {

	protected net.minecraft.server.v1_7_10.apx chunk;
	
	public void c() {
		
	}

	public boolean e() {
		return false;
	}

	public agt a(ahb arg0, String arg1, int arg2, int arg3, int arg4) {
		return null;
	}

	public apx d(int x, int y) {
		return this.c(x, y);
	}

	public void a(apu arg0, int arg1, int arg2) {
		
	}

	public int g() {
		return 0;
	}

	@SuppressWarnings("rawtypes")
	public List a(sx arg0, int arg1, int arg2, int arg3) {
		return null;
	}

	public String f() {
		return null;
	}

	public apx c(int x, int y) {
		if(this.chunk == null) {
			return null;
		}
		if (this.chunk.g != x || this.chunk.h != y) {
			return new net.minecraft.server.v1_7_10.apw(this.chunk.e, x, y);
		}
		return this.chunk;
	}

	public boolean a(int arg0, int arg1) {
		return false;
	}

	public void e(int arg0, int arg1) {
		
	}

	public boolean a(boolean arg0, qk arg1) {
		return false;
	}

	public boolean d() {
		return false;
	}

}
