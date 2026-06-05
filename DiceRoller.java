/**
 * A utility class for parsing and executing dice roll commands in a tabletop RPG context.
 * </br>
 * </br>
 * This class supports various roll formats, including regular rolls (e.g., "2d6"), rolls with modifiers (e.g., "1d20+5"), and special rolls with advantage or disadvantage (e.g., "adv(1d20)", "dis(2d6)").
 * </br>
 * </br>
 * The class includes methods for identifying special commands, extracting roll components, parsing modifiers, and determining the type of roll being executed.
 */
public class DiceRoller {
    private static final String SPECIAL_COMMAND_REGEX = "(adv|dis)+\\(+\\d+d\\d+([+-]\\d+)*\\)+";  // Matches commands like "adv(1d20+5)" or "dis(2d6-3)"
    private static final String ROLL_REGEX = "\\d+d\\d+";  // Matches basic roll commands like "2d6"
    private static final String MODIFIER_REGEX = "[+-]\\d+";  // Matches modifiers like "+5" or "-3"
    private static final String POSITIVE_MODIFIER_REGEX = "[+]+\\d+";  // Matches positive modifiers like "+5"
    private static final String NEGATIVE_MODIFIER_REGEX = "[-]+\\d+";  // Matches negative modifiers like "-3"
    private static final String CONTAINS_MODIFIER_REGEX = ".*[+-]\\d+.*";  // Checks if the command contains a modifier
    private static final String SEPARATOR_REGEX = "\\s+[+-]\\s+";  // Matches comma separators with optional whitespace

    /**
     * Checks if a command is a special command (e.g., "adv" or "dis").
     * @param command the command to check
     * @return true if the command is special, false otherwise
     */
    private static boolean _isSpecialCommand(String command) {
        return command.matches(SPECIAL_COMMAND_REGEX);
    }

    /**
     * Checks if a command contains a modifier (e.g., "+5" or "-3").
     * @param command the command to check
     * @return true if the command contains a modifier, false otherwise
     */
    private static boolean _commandContainsModifier(String command) {
        return command.matches(CONTAINS_MODIFIER_REGEX);
    }

    /**
     * Splits a roll command into its constituent parts based on the separator regex.
     * @param command the roll command to split
     * @return an array of strings representing the components of the command
     */
    private static String[] _splitCommandIntoComponents(String command) {
        return command.split(SEPARATOR_REGEX);
    }

    /**
     * Extracts the roll and modifier components from a command.
     * </br>
     * </br>
     * For example, given the command "1d20+5", this method would return an array where the first element is "1d20" and the second element is "+5".
     * </br>
     * </br>
     * INVARIANT: The input command must contain a valid roll format (e.g., "XdY") and may optionally include modifiers (e.g., "+Z" or "-Z").
     * </br>
     * INVARIANT: The input command is not special (i.e., it does not contain "adv" or "dis").
     * @param command the command to extract components from
     * @return an array where the first element is the roll part and the second element is the modifier part
     */
    private static String[] _extractRollComponents(String command) {
        String rollPart = command.replaceAll(MODIFIER_REGEX, "");  // Remove the modifier part to isolate the roll command
        String modifierPart = command.replaceAll(ROLL_REGEX, "");  // Remove the roll part to isolate the modifier
        if (modifierPart.equals(command)) {
            modifierPart = "";
        }
        return new String[]{rollPart, modifierPart};
    }

    /**
     * Parses a modifier string and returns its integer value.
     * @param modifier the modifier string to parse
     * @return the integer value of the modifier
     */
    private static int _parseModifier(String modifier) {
        if (modifier.isEmpty()) {
            return 0;
        }

        String[] positiveModifiers = modifier.split(NEGATIVE_MODIFIER_REGEX);  // Remove negative modifiers to isolate positive ones
        String[] negativeModifiers = modifier.split(POSITIVE_MODIFIER_REGEX);  // Remove positive modifiers to isolate negative ones

        int totalModifier = 0;
        for (String posMod : positiveModifiers) {
            if (!posMod.isEmpty()) {
                totalModifier += Integer.parseInt(posMod);
            }
        }
        for (String negMod : negativeModifiers) {
            if (!negMod.isEmpty()) {
                totalModifier += Integer.parseInt(negMod);
            }
        }

        return totalModifier;
    }

    /**
     * Determines the type of roll being executed based on the command string.
     * @param command the command to analyze
     * @return the RollType corresponding to the command (ADVANTAGE, DISADVANTAGE, or REGULAR)
     */
    private static RollType _parseRollType(String command) {
        if (command.contains("adv")) {
            return RollType.ADVANTAGE;
        } else if (command.contains("dis")) {
            return RollType.DISADVANTAGE;
        } else {
            return RollType.REGULAR;
        }
    }

    /**
     * Handles the execution of a special roll command (e.g., "adv(1d20+5)" or "dis(2d6-3)").
     * </br>
     * </br>
     * This method extracts the inner roll command from the special command, determines the type of roll (advantage or disadvantage), and then executes the roll using the appropriate logic.
     * @param command the special roll command to handle
     * @return the final result of the roll after applying any modifiers
     */
    private static int _handleSpecialRoll(String command) {
        RollType rollType = _parseRollType(command);
        String innerCommand = command.replaceAll("(adv|dis)\\(", "").replaceAll("\\)", "");  // Extract the inner roll command from the special command
        return _parseRoll(innerCommand, rollType);
    }

    /**
     * Parses a regular roll command (without advantage or disadvantage) and executes the appropriate dice rolls based on the command format.
     * </br>
     * </br>
     * For example, given the command "2d6+3", this method would roll 2 six-sided dice, sum the results, and then add 3 to the total.
     * @param command the roll command to parse and execute
     * @param rollType the type of roll being executed (e.g., regular, advantage, disadvantage)
     * @return the final result of the roll after applying any modifiers
     */
    private static int _parseRoll(String command, RollType rollType) {
        String[] components = _extractRollComponents(command);

        String modifierPart = components[1];
        int modifierValue = _parseModifier(modifierPart);
        
        String rollPart = components[0];
        String[] rollComponents = rollPart.split("d");
        int numberOfDice = Integer.parseInt(rollComponents[0]);
        int numberOfSides = Integer.parseInt(rollComponents[1]);

        Dice dice = new Dice(numberOfSides);
        RollResult rollResult = dice.roll(numberOfDice, rollType);
        int finalResult = rollResult.result() + modifierValue;
        return finalResult;
    }

    /**
     * Parses a roll command and executes the appropriate dice rolls based on the command format.
     * </br>
     * </br>
     * Roll may include functionality for advantage, disadvantage, max, and min rolls. For example: adv(1d20), dis(2d6).
     * </br>
     * </br>
     * Roll commands can also include modifiers, such as 1d20+5 or 2d6-3.
     * @param command the roll command to parse and execute
     * @return the final result of the roll after applying any modifiers, or -1 if the command is invalid
     */
    public static int parseRollCommand(String command) {
        int finalResult = -1;  // Placeholder for the final result of the roll
        if (!command.contains(" + ") && !command.contains(" - ")) {
            if (_isSpecialCommand(command)) {
                finalResult = _handleSpecialRoll(command);
            } else {
                finalResult = _parseRoll(command, RollType.REGULAR);
            }
            return finalResult;
        }

        String[] commands = _splitCommandIntoComponents(command);

        for (String cmd : commands) {
            boolean isSpecial = _isSpecialCommand(cmd);
            if (isSpecial) {
                finalResult += _handleSpecialRoll(cmd);
            } else {
                finalResult += _parseRoll(cmd, RollType.REGULAR);
            }
        }
        return finalResult;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java DiceRoller <roll_command>");
            return;
        }
        for (String arg : args) {
            int result = parseRollCommand(arg);
            System.out.println("Command: " + arg);
            System.out.println("Result: " + result);
        }
    }
}
