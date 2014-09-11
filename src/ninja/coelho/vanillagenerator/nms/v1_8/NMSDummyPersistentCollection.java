package ninja.coelho.vanillagenerator.nms.v1_8;

import net.minecraft.server.v1_8.bqc;
import net.minecraft.server.v1_8.brn;

public class NMSDummyPersistentCollection extends brn {

	public NMSDummyPersistentCollection() {
		super(null);
	}

	@Override
	public void a(String arg0, bqc arg1) {
		// dummy
	}

	@Override
	public int a(String arg0) {
		// dummy
		return 0;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public bqc a(Class arg0, String arg1) {
		// dummy
		return null;
	}

}
