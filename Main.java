import java.util.Arrays;

class Main {
    private static boolean _debugMode = false;
    private static final String OPTION_STRING_REGEX = "^-{1,2}[a-zA-Z?]+";

    /* Debugging function */
    private static void _debug(String message) {
        if (!_debugMode) return;
        System.out.println(message);
    }

    /* Unknown command function */
    private static void _unknownCommand(String message) {
        System.err.println("Unknown command: " + message);
    }

    /* Error handling function */
    private static void _error(String message) {
        System.err.println("ERROR: " + message);
    }

    /* User guide function */
    private static void _printUserGuide() {
        System.out.println("Usage: java Main [OPTIONS] <dice_expression>");
        System.out.println("Example: java Main \"1d20 + 2d6 - 3d4\"");
        System.out.println();
        System.out.println("Options:");
        System.out.println("\t-? : Show help message");
        System.out.println("\t--debug : Enable debug mode");
        System.out.println("\t--cli : Start interactive CLI mode");
        System.out.println();
    }

    /* Welcome and farewell messages */
    private static void _printWelcomeMessage() {
        System.out.println("Welcome to the Dice Roller!");
        System.out.println("You can roll dice using standard notation (e.g., 1d20, 2d6, etc.)");
        System.out.println("You can also use modifiers (e.g., +3, -2) and advanced expressions (e.g., adv(1d20+3))");
        System.out.println();
    }
    private static void _printFarewellMessage() {
        System.out.println("Thank you for using the Dice Roller! Goodbye!");
    }

    /* Help message function */
    private static void _printHelpMessage() {
        _printUserGuide();
        System.out.println("Dice Notation:");
        System.out.println("\tNdM - Roll N dice with M sides (e.g., 2d6 rolls two six-sided dice)");
        System.out.println("Modifiers:");
        System.out.println("\t+X or -X - Add or subtract a constant value (e.g., +3, -2)");
        System.out.println("Advanced Expressions:");
        System.out.println("\tadv(expr) - Roll expr twice and take the higher result");
        System.out.println("\tdis(expr) - Roll expr twice and take the lower result");
    }

    private static void _rollDice(String expression) {
        try {
            int result = DiceRoller.parseRollCommand(expression);
            System.out.println("Result: " + result);
        } catch (IllegalArgumentException e) {
            _error("ERROR: " + e.getMessage());
        }
    }

    private static void _optionsHandler(String[] input) {
        String[] args = input;
        while (args.length > 0 && args[0].matches(OPTION_STRING_REGEX)) {
            _debug("Processing input: " + String.join(" ", args));
            switch (args[0]) {
                case "-?":
                    _printHelpMessage();
                    return;
                case "--debug":
                    _debugMode = true;
                    _debug("Debug mode enabled");
                    args = Arrays.copyOfRange(args, 1, args.length);
                    break;
                case "--cli":
                    _cli();
                    return;
                default:
                    System.out.println("Unknown option: " + args[0]);
                    _printHelpMessage();
                    return;
            }
        }
        if (args.length > 0) {
            _debug("Processing input: " + String.join(" ", args));
            String expression = String.join(" ", args);
            _rollDice(expression);
        }
        else _printFarewellMessage();
    }

    private static void _cli() {
        _debug("Entering CLI mode");
        // TODO: implement the CLI interface and handle user input

        boolean loop = true;
        while (loop) {
            System.out.print("> ");
            String input = System.console().readLine();
            switch (input) {
                case "exit", "quit" -> loop = false;
                default -> _unknownCommand(input);
            }
        }

        _printFarewellMessage();
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            _printWelcomeMessage();
            _printUserGuide();
            return;
        }
        _optionsHandler(args);
    }
}