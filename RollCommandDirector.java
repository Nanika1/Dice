public class RollCommandDirector {
    public static RollCommand d4() {
        return new RollCommandBuilder()
                    .addDiceRoll("1d4")
                    .build();
    }
    public static RollCommand d6() {
        return new RollCommandBuilder()
                    .addDiceRoll("1d6")
                    .build();
    }
    public static RollCommand d8() {
        return new RollCommandBuilder()
                    .addDiceRoll("1d8")
                    .build();
    }
    public static RollCommand d10() {
        return new RollCommandBuilder()
                    .addDiceRoll("1d10")
                    .build();
    }
    public static RollCommand d12() {
        return new RollCommandBuilder()
                    .addDiceRoll("1d12")
                    .build();
    }
    public static RollCommand d20() {
        return new RollCommandBuilder()
                    .addDiceRoll("1d20")
                    .build();
    }
    public static RollCommand d100() {
        return new RollCommandBuilder()
                    .addDiceRoll("1d100")
                    .build();
    }
}
