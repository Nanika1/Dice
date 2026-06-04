import java.util.Random;

public class Dice {
    private final int sides;
    private final String name;

    Dice(int sides) {
        if (sides < 1) {
            throw new IllegalArgumentException("Dice must have at least one side.");
        }
        this.sides = sides;
        this.name = "d" + sides;
    }

    /**
     * Returns the name of the dice (e.g., "d6" for a six-sided die).
     * @return the name of the dice
     */
    public String getName() {
        return name;
    }

    /**
     * Rolls the dice and returns a random number between 1 and the number of sides.
     * @return the result of the roll
     */
    public int roll() {
        return new Random().nextInt(sides) + 1;
    }

    /**
     * Rolls the dice a specified number of times and returns the sum.
     * @param numberOfDice the number of times to roll the dice
     * @return the sum of all rolls
     */
    public int roll(int numberOfDice) {
        if (numberOfDice < 1) {
            throw new IllegalArgumentException("Must roll at least one die.");
        }
        int total = 0;
        for (int i = 0; i < numberOfDice; i++) {
            total += roll();
        }
        return total;
    }

    /**
     * Returns the maximum possible value for a single roll.
     * @return the maximum roll value
     */
    public int rollMax() {
        return sides;
    }

    /**
     * Returns the minimum possible value for a single roll.
     * @return the minimum roll value
     */
    public int rollMin() {
        return 1;
    }

    /**
     * Rolls the dice twice and returns the higher of the two rolls.
     * @return the higher of the two rolls
     */
    public int rollAdvantage() {
        int firstRoll = roll();
        int secondRoll = roll();
        return Math.max(firstRoll, secondRoll);
    }

    /**
    * Rolls the dice twice and returns the lower of the two rolls.
    * @return the lower of the two rolls
    */
    public int rollDisadvantage() {
        int firstRoll = roll();
        int secondRoll = roll();
        return Math.min(firstRoll, secondRoll);
    }
}
