package vanillagenerator.util;

public class LongHash {

	public static long toLong(int msw, int lsw) {
		return ((long) msw << 32) + lsw - Integer.MAX_VALUE;
	}
	
}
