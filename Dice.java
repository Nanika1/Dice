import java.util.Random;
import java.util.Arrays;

/**
 * Represents a dice with a specified number of sides. Provides methods to roll the dice in various ways, including regular rolls, rolls with advantage, and rolls with disadvantage.
 * The class also includes validation for the number of sides and the name format.
 */
public class Dice {
    private final int sides;
    private final String name;

    /**
     * Constructs a Dice object with the specified number of sides.
     * @param sides the number of sides on the dice
     * @throws IllegalArgumentException if the number of sides is less than 1
     */
    Dice(int sides) {
        if (sides < 1) {
            throw new IllegalArgumentException("Dice must have at least one side.");
        }
        this.sides = sides;
        this.name = "d" + sides;
    }

    /**
     * Constructs a Dice object with the specified name in the format "dX", where X is the number of sides.
     * @param name the name of the dice (e.g., "d6" for a six-sided die)
     * @throws IllegalArgumentException if the name is null or does not match the required format
     */
    Dice(String name) {
        if (name == null || !name.matches("d\\d+")) {
            throw new IllegalArgumentException("Name must be in the format 'dX' where X is a positive integer.");
        }
        this.name = name;
        this.sides = Integer.parseInt(name.substring(1));
    }

    /**
     * Returns the name of the dice (e.g., "d6" for a six-sided die).
     * @return the name of the dice
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the maximum possible roll for this dice.
     * @return the maximum value of the dice
     */
    private int _getMax() {
        return sides;
    }

    /**
     * Returns the minimum possible roll for this dice.
     * @return the minimum value of the dice
     */
    private int _getMin() {
        return 1;
    }

    /**
     * Rolls the dice and returns a random number between 1 and the number of sides.
     * @return the result of the roll
     */
    private int _roll() {
        return new Random().nextInt(sides) + 1;
    }

    /**
     * Rolls the dice a specified number of times and returns the sum.
     * @param numberOfDice the number of times to roll the dice
     * @return an array containing the individual values rolled
     */
    private int[] _roll(int numberOfDice) {
        int[] values = new int[numberOfDice];
        for (int i = 0; i < numberOfDice; i++) {
            values[i] = _roll();
        }
        return values;
    }

    /**
     * Rolls the dice with advantage, returning the highest of two rolls along with both rolls for reference.
     * @return an array where the first element is the result of the roll with advantage, and the next two elements are the individual rolls
     */
    private int[] _rollAdvantage() {
        int firstRoll = _roll();
        int secondRoll = _roll();
        return new int[]{Math.max(firstRoll, secondRoll), firstRoll, secondRoll};
    }

    /**
     * Rolls the dice with advantage for a specified number of dice, returning the highest rolls along with all individual rolls for reference.
     * @param numberOfDice the number of dice to roll with advantage
     * @return an array where the first element is the total result of the rolls with advantage, and the next elements are the individual rolls
     */
    private int[] _rollAdvantage(int numberOfDice) {
        int[] values = new int[numberOfDice * 2];  // Roll twice for each die
        int total = 0;
        for (int i = 0; i < numberOfDice; i++) {
            int[] tmp = _rollAdvantage();  // Get the rolls for this die
            total += tmp[0];  // Add the highest roll to the total result
            values[i * 2] = tmp[1];       // First roll for this die
            values[i * 2 + 1] = tmp[2];   // Second roll for this die
        }
        int[] result = new int[values.length + 1];
        result[0] = total;  // First element is the total result of the rolls
        System.arraycopy(values, 0, result, 1, values.length);  // Copy the individual rolls into the result array
        return result;
    }

    /**
     * Rolls the dice with disadvantage for a specified number of dice, returning the lowest rolls along with all individual rolls for reference.
     * @param numberOfDice the number of dice to roll with disadvantage
     * @return an array where the first element is the total result of the rolls with disadvantage, and the next elements are the individual rolls
     */
    private int[] _rollDisadvantage(int numberOfDice) {
        int[] values = new int[numberOfDice * 2];  // Roll twice for each die
        int total = 0;
        for (int i = 0; i < numberOfDice; i++) {
            int[] tmp = _rollDisadvantage();  // Get the rolls for this die
            total += tmp[0];  // Add the lowest roll to the total result
            values[i * 2] = tmp[1];       // First roll for this die
            values[i * 2 + 1] = tmp[2];   // Second roll for this die
        }
        int[] result = new int[values.length + 1];
        result[0] = total;  // First element is the total result of the rolls
        System.arraycopy(values, 0, result, 1, values.length);  // Copy the individual rolls into the result array
        return result;
    }

    /**
     * Rolls the dice with disadvantage, returning the lowest of two rolls along with both rolls for reference.
     * @return an array where the first element is the result of the roll with disadvantage, and the next two elements are the individual rolls
     */
    private int[] _rollDisadvantage() {
        int firstRoll = _roll();
        int secondRoll = _roll();
        return new int[]{Math.min(firstRoll, secondRoll), firstRoll, secondRoll};
    }

    /**
     * Rolls the dice and returns a RollResult object containing the result and related information.
     * @return the result of the roll
     */
    public RollResult roll() {
        int result = _roll();
        return new RollResult(result, new int[]{result}, _getMax(), _getMin(), RollType.REGULAR);
    }

    /**
     * Rolls the dice a specified number of times and returns a RollResult object containing the result and related information.
     * @param numberOfDice the number of times to roll the dice
     * @return the result of the roll
     */
    public RollResult roll(int numberOfDice) {
        if (numberOfDice < 1) {
            throw new IllegalArgumentException("Must roll at least one die.");
        }
        int[] values = _roll(numberOfDice);
        int total = 0;
        for (int value : values) {
            total += value;
        }
        int max = _getMax() * numberOfDice;
        int min = _getMin() * numberOfDice;
        return new RollResult(total, values, max, min, RollType.REGULAR);
    }

    /**
     * Rolls the dice with advantage, returning a RollResult object containing the result and related information.
     * @return the result of the roll with advantage
     */
    public RollResult rollAdvantage() {
        int[] values = _rollAdvantage();
        return new RollResult(values[0], Arrays.copyOfRange(values, 1, values.length), _getMax(), _getMin(), RollType.ADVANTAGE);
    }

    /**
     * Rolls the dice with advantage for a specified number of dice, returning a RollResult object containing the result and related information.
     * @param numberOfDice the number of dice to roll with advantage
     * @return the result of the roll with advantage
     */
    public RollResult rollAdvantage(int numberOfDice) {
        if (numberOfDice < 1) {
            throw new IllegalArgumentException("Must roll at least one die.");
        }
        int[] values = _rollAdvantage(numberOfDice);
        int max = _getMax() * numberOfDice;
        int min = _getMin() * numberOfDice;
        return new RollResult(values[0], Arrays.copyOfRange(values, 1, values.length), max, min, RollType.ADVANTAGE);
    }

    /**
     * Rolls the dice with disadvantage, returning a RollResult object containing the result and related information.
     * @return the result of the roll with disadvantage
     */
    public RollResult rollDisadvantage() {
        int[] values = _rollDisadvantage();
        return new RollResult(values[0], Arrays.copyOfRange(values, 1, values.length), _getMax(), _getMin(), RollType.DISADVANTAGE);
    }

    /**
     * Rolls the dice with disadvantage for a specified number of dice, returning a RollResult object containing the result and related information.
     * @param numberOfDice the number of dice to roll with disadvantage
     * @return the result of the roll with disadvantage
     */
    public RollResult rollDisadvantage(int numberOfDice) {
        if (numberOfDice < 1) {
            throw new IllegalArgumentException("Must roll at least one die.");
        }
        int[] values = _rollDisadvantage(numberOfDice);
        int max = _getMax() * numberOfDice;
        int min = _getMin() * numberOfDice;
        return new RollResult(values[0], Arrays.copyOfRange(values, 1, values.length), max, min, RollType.DISADVANTAGE);
    }

    /**
     * Rolls the dice based on the specified roll type (regular, advantage, or disadvantage) and returns a RollResult object containing the result and related information.
     * @param rollType the type of roll to execute
     * @return the result of the roll
     */
    public RollResult roll(RollType rollType) {
        if (rollType == null) {
            throw new IllegalArgumentException("Roll type cannot be null.");
        }
        return switch (rollType) {
            case ADVANTAGE -> rollAdvantage();
            case DISADVANTAGE -> rollDisadvantage();
            default -> roll();
        };
    }

    /**
     * Rolls the dice a specified number of times based on the specified roll type (regular, advantage, or disadvantage) and returns a RollResult object containing the result and related information.
     * @param numberOfDice the number of times to roll the dice
     * @param rollType the type of roll to execute
     * @return the result of the roll
     */
    public RollResult roll(int numberOfDice, RollType rollType) {
        if (numberOfDice < 1) {
            throw new IllegalArgumentException("Must roll at least one die.");
        }
        if (rollType == null) {
            throw new IllegalArgumentException("Roll type cannot be null.");
        }
        if (numberOfDice == 1) {
            return roll(rollType);  // Use the single die roll method for one die
        }
        return switch (rollType) {
            case ADVANTAGE -> rollAdvantage(numberOfDice);
            case DISADVANTAGE -> rollDisadvantage(numberOfDice);
            default -> roll(numberOfDice);
        };
    }
}