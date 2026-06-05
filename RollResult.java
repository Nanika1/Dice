/**
 * Represents the result of a roll, including the total result, individual values rolled, maximum and minimum values, and the type of roll.
 * This record is immutable and provides a structured way to encapsulate all relevant information about a roll.
 * @param result the final result of the roll (e.g., the sum of rolled values, or the higher/lower value for advantage/disadvantage rolls)
 * @param values an array containing the individual values rolled (for advantage/disadvantage rolls, this will contain both rolls; first element is the higher/lower roll for advantage/disadvantage respectively)
 * @param max the maximum possible value for the roll (based on the dice)
 * @param min the minimum possible value for the roll (based on the dice)
 * @param rollType the type of roll (e.g., regular, advantage, disadvantage)
 */
public record RollResult(int result, int[] values, int max, int min, RollType rollType) {
    
}
