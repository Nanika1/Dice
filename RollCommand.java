import java.util.List;
import java.util.ArrayList;

class RollCommand {
    class Commandlette {
        interface Component {
            int X();
            int Y();
            int Z();
        }
        private class DiceComponent implements Component {
            private int _numberOfDice = 0;
            private int _numberOfSides = 0;

            DiceComponent(int numberOfDice, int numberOfSides) {
                this._numberOfDice = numberOfDice;
                this._numberOfSides = numberOfSides;
            }

            public int X() {
                return _numberOfDice;
            }
            public int Y() {
                return _numberOfSides;
            }
            public int Z() {
                throw new RuntimeException("Component is not a modifier");
            }

            @Override
            public String toString() {
                return _numberOfDice + "d" + _numberOfSides;
            }
        }
        private class ModifierComponent implements Component {
            private int _value = 0;

            ModifierComponent(int value) {
                this._value = value;
            }

            public int X() {
                throw new RuntimeException("Component is not a die");
            }
            public int Y() {
                throw new RuntimeException("Component is not a die");
            }
            public int Z() {
                return _value;
            }

            @Override
            public String toString() {
                if (_value < 0) return "" + _value;
                return "+" + _value;
            }
        }

        private DiceComponent _dice;
        private List<ModifierComponent> _modifiers = new ArrayList<>();
        private int _value;
        private boolean _hasModifiers = false;
        private boolean _isConst = false;

        public final RollType rollType;

        Commandlette(String diceComponent, RollType rollType) {
            String[] tmp = diceComponent.split("d");
            this._dice = new DiceComponent(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]));
            this.rollType = rollType;
        }
        Commandlette(String value) {
            this._value = Integer.parseInt(value);
            this._isConst = true;
            this.rollType = RollType.REGULAR;
        }

        public void addModifier(String modifier) {
            if (_isConst) return;
            int value = Integer.parseInt(modifier);
            _modifiers.add(new ModifierComponent(value));
            _hasModifiers = true;
        }

        public int[] getDice() {
            if (_isConst) return null;

            return new int[]{_dice.X(), _dice.Y()};
        }
        public int[] getModifiers() {
            if (!_hasModifiers || _isConst) return null;

            int[] output = new int[_modifiers.size()];
            for (int i = 0; i < output.length; i++) {
                output[i] = _modifiers.get(i).Z();
            }
            return output;
        }
        public int getValue() {
            return _value;
        }

        public boolean hasModifiers() {
            return _hasModifiers;
        }

        @Override
        public String toString() {
            if (_isConst) return "" + _value;

            String commandlette = "" + _dice;
            for (Component comp : _modifiers) {
                commandlette += "" + comp;
            }
            return switch (rollType) {
                case ADVANTAGE -> "adv(" + commandlette + ")";
                case DISADVANTAGE -> "dis(" + commandlette + ")";
                default -> commandlette;
            };
        }
    }

    private List<Commandlette> _commandlettes = new ArrayList<>();

    RollCommand() {}

    public void addCommandlette(String dice, RollType rollType, String[] modifiers) {
        Commandlette cmdlt = new Commandlette(dice, rollType);
        for (String mod : modifiers) {
            cmdlt.addModifier(mod);
        }
        _commandlettes.add(cmdlt);
    }
    public void addConstant(String value) {
        Commandlette cmdlt = new Commandlette(value);
        _commandlettes.add(cmdlt);
    }

    public List<Commandlette> getCommandlettes() {
        return _commandlettes;
    }

    public boolean isEmpty() {
        return _commandlettes.isEmpty();
    }

    @Override
    public String toString() {
        String command = "";
        for (Commandlette cmdlt : _commandlettes) {
            if (command.equals("")) command += "" + cmdlt;
            else command += " + " + cmdlt;
        }
        return command;
    }
}