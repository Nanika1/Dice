import java.util.Arrays;

public class DiceRoller {
    private static final String ROLL_FUNCTION_REGEX = "(adv|dis)+\\(+\\d*d\\d+([+-][0-9]+)*\\)+";  // Matches commands like "adv(1d20+5)" or "dis(2d6-3)"
    private static final String ROLL_REGEX = "[0-9]+d[0-9]+";  // Matches basic roll commands like "2d6"
    private static final String MODIFIER_REGEX = "([+-][0-9]+)";  // Matches modifiers like "+5" or "-3"

    private static boolean commandIsSpecial(String command) {
        return command.contains("adv") || command.contains("dis");
    }
    private static boolean commandContainsModifier(String command) {
        return command.matches(".*[+-]\\d+.*");
    }

    private static String[] extractRollComponents(String command) {
        String rollPart = command.replaceAll(MODIFIER_REGEX, "");  // Remove the modifier part to isolate the roll command
        String modifierPart = command.replaceAll(ROLL_REGEX, "");  // Remove the roll part to isolate the modifier
        if (modifierPart.equals(command)) {
            modifierPart = "";
        }
        return new String[]{rollPart, modifierPart};
    }

    private static int parseModifier(String modifier) {
        if (modifier.isEmpty()) {
            return 0;
        }
        if (modifier.startsWith("+")) {
            return Integer.parseInt(modifier.substring(1));
        } else if (modifier.startsWith("-")) {
            return Integer.parseInt(modifier);
        } else {
            throw new IllegalArgumentException("Invalid modifier format: " + modifier);
        }
    }

    private static RollType parseRollType(String command) {
        if (command.contains("adv")) {
            return RollType.ADVANTAGE;
        } else if (command.contains("dis")) {
            return RollType.DISADVANTAGE;
        } else {
            return RollType.REGULAR;
        }
    }

    private static void basicParseRollCommand(String command) {
        if (commandContainsModifier(command)) {
            String[] components = extractRollComponents(command);
            String rollPart = components[0];
            String modifierPart = components[1];
            System.out.println("Rolling: " + rollPart + " with modifier: " + modifierPart);
            int modifier = parseModifier(modifierPart);
        } else {
            System.out.println("Rolling: " + command);
        }
    }
    private static void specialParseRollCommand(String command) {
        RollType rollType = parseRollType(command);
        System.out.println("Rolling with " + rollType + ": " + command);
    }

    /**
     * Parses a roll command and executes the appropriate dice rolls based on the command format.
     * </br>
     * </br>
     * Roll may include functionality for advantage, disadvantage, max, and min rolls. For example: adv(1d20), dis(2d6), max(3d8), min(4d4).
     * </br>
     * </br>
     * Roll commands can also include modifiers, such as 1d20+5 or 2d6-3.
     * @param command the roll command to parse and execute
     */
    private static void parseRollCommand(String command) {
        if (commandIsSpecial(command)) {
            specialParseRollCommand(command);
        } else {
            basicParseRollCommand(command);
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java DiceRoller <roll_command>");
            return;
        }
        for (String arg : args) {
            parseRollCommand(arg);
        }
    }
}
