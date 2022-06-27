package carsharing.utils;

import java.util.HashMap;
import java.util.Map;

public final class ArgumentExtractor
{
    public static Map<String, String> extractArguments(String... args) {
        Map<String, String> arguments = new HashMap<String, String>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                String key = args[i].substring(1);
                StringBuilder argumentBuilder = new StringBuilder();
                for (int j = i + 1; j < args.length; j++) {
                    if (args[j].startsWith("-")) {
                        break;
                    }
                    argumentBuilder.append(args[j]).append(" ");
                }
                arguments.put(key, argumentBuilder.toString().trim());
            }
        }
        return arguments;
    }
}
