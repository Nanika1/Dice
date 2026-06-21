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
}
