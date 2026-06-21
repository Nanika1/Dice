public class RollCommandBuilder {
    private RollCommand _command = null;

    RollCommandBuilder() {
        this._command = new RollCommand();
    }

    public RollCommandBuilder addCommandlette(String dice, RollType rollType, String... modifiers) {
        _command.addCommandlette(dice, rollType, modifiers);
        return this;
    }

    public RollCommandBuilder addDiceRoll(String dice, String... modifiers) {
        _command.addCommandlette(dice, RollType.REGULAR, modifiers);
        return this;
    }

    public RollCommandBuilder addDiceRollAdvantage(String dice, String... modifiers) {
        _command.addCommandlette(dice, RollType.ADVANTAGE, modifiers);
        return this;
    }

    public RollCommandBuilder addDiceRollDisadvantage(String dice, String... modifiers) {
        _command.addCommandlette(dice, RollType.DISADVANTAGE, modifiers);
        return this;
    }

    public RollCommandBuilder addConstant(String value) {
        _command.addConstant(value);
        return this;
    }

    public RollCommand build() {
        return _command;
    }

    public static void main(String[] args) {
        System.out.println("=== Testing RollCommandBuilder ===");
        System.out.println();
        // roll commands
        RollCommand[] cmd = {
            new RollCommandBuilder()
                .addConstant("2")
                .build(),  // "2"
            new RollCommandBuilder()
                .addDiceRoll("1d20")
                .build(),  // "1d20"
            new RollCommandBuilder()
                .addDiceRollAdvantage("2d6", "+1")
                .addConstant("5")
                .addDiceRoll("1d12", "-3")
                .build(),  // "adv(2d6+1) + 5 + 1d12-3"
            new RollCommandBuilder()
                .addDiceRoll("3d8", "+1", "-2", "+3")
                .build(),  // "3d8+1-2+3"
            new RollCommandBuilder()
                .addDiceRollAdvantage("1d4", "+1")
                .addDiceRollDisadvantage("1d4", "-1")
                .build(),  // "adv(1d4+1) + dis(1d4-1)"
        };
        // expected
        String[] expected = {
            "2",
            "1d20",
            "adv(2d6+1) + 5 + 1d12-3",
            "3d8+1-2+3",
            "adv(1d4+1) + dis(1d4-1)",
        };
        // achieved
        String[] achieved = new String[cmd.length];
        for (int i = 0; i < cmd.length; i++) {
            achieved[i] = cmd[i].toString();
        }
        // comparison
        boolean[] results = new boolean[cmd.length];
        for (int i = 0; i < cmd.length; i++) {
            results[i] = expected[i].equals(achieved[i]);
        }
        // output
        for (int i = 0; i < results.length; i++) {
            if (results[i]) System.out.println("cmd"+i+": PASS");
            else {
                System.out.println("cmd"+i+": FAIL");
                System.out.println("\texpected: " + expected[i]);
                System.out.println("\tachieved: " + achieved[i]);
            }
        }
    }
}
