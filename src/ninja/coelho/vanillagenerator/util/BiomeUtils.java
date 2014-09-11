package ninja.coelho.vanillagenerator.util;

public class BiomeUtils {

	public static final String translateBiomeName(String input) {
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
			return specialCase(input.toUpperCase().replace(' ', '_'));
		}
		StringBuilder builder = new StringBuilder();
		char[] chars = input.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (i != 0 && Character.isUpperCase(chars[i])) {
				builder.append("_");
				builder.append(chars[i]);
				continue;
			}
			builder.append(Character.toUpperCase(chars[i]));
		}
		return specialCase(builder.toString());
	}
	
	private static final String specialCase(String input) {
		if (input.equals("MUSHROOM_ISLAND_SHORE")) {
			return "MUSHROOM_SHORE";
		} else if (input.equals("THE_END")) {
			return "SKY";
		}
		return input;
	}
	
}
