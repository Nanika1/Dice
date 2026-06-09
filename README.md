# JavaDice

A small Java program that lets you roll dice

## Notation

### Basic

A *roll command* consists of one or more *commandlettes*.

A *commandlette* consists primarily of two components: *dice* and *modifiers*, and are separated as such: `<commandlette> + <commandlette>`.
(e.g. `1d12 + 2d6`)

The *dice* component has notation: `XdY`, where `X` is the number of dice, and `Y` is the number of sides the dice has.
(e.g. `2d6` is two six-sided dice)

The *modifier* component has notation: `+Z` or `-Z`, and is always located AFTER the *dice* component.
(e.g. `+3` is a "plus three" modifier, and is placed as such: `2d6+3`)

### Special

A *roll command* is said to be *special* if it contains one or more *roll type specifier*.

A *roll type specifier* specifies which of the following *roll types* a roll is:
- Regular -- the default roll type
- Advantage -- each die is rolled twice, the higher of the two results becomes the final result of the roll -- `adv(1d20)`
- Disadvantage -- each die is rolled twice, the lower of the two results becomes the final result of the roll -- `dis(1d20)`


## User guide

Use `make` with the provided `makefile`, or do the following:

To compile:
```bash
javac *.java
```
To run:
```bash
java Main
```

## Author

- Nanika1
