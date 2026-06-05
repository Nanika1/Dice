/**
 * Represents the result of a roll, including the total result, individual values rolled, maximum and minimum values, and the type of roll.
 * This record is immutable and provides a structured way to encapsulate all relevant information about a roll.
 */
public record RollResult(int result, int[] values, int max, int min, RollType rollType) {
    
}
