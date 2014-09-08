package vanillagenerator.nms.v17;

import net.minecraft.server.PersistentBase;
import net.minecraft.server.PersistentCollection;

public class NMSDummyPersistentCollection extends PersistentCollection {

	public NMSDummyPersistentCollection() {
		super(null);
	}

	@Override
	public void a(String arg0, PersistentBase arg1) {
		// dummy
	}

	@Override
	public int a(String arg0) {
		// dummy
		return 0;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PersistentBase get(Class arg0, String arg1) {
		// dummy
		return null;
	}

}
