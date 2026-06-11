<a id="readme-top"></a>

# JavaDice

<!-- PROJECT SHIELDS (TBA) -->

## About JavaDice

A small Java program that lets you roll dice

### Built With

- java 25.0.1

## User Guide

### How to Build

```bash
javac *.java
```

### How to Run

```bash
java Main
```

## Notation

### Basic Commands

A *roll command* consists of one or more *commandlettes*.

A *commandlette* consists primarily of two components: *dice* and *modifiers*, and are separated as such: `<commandlette> + <commandlette>`.
(e.g. `1d12 + 2d6`)

The *dice* component has notation: `XdY`, where `X` is the number of dice, and `Y` is the number of sides the dice has.
(e.g. `2d6` is two six-sided dice)

The *modifier* component has notation: `+Z` or `-Z`, and is always located AFTER the *dice* component.
(e.g. `+3` is a "plus three" modifier, and is placed as such: `2d6+3`)

### Special Commands

A *roll command* is said to be *special* if it contains one or more *roll type specifier*.

A *roll type specifier* specifies which of the following *roll types* a roll is:
- Regular -- the default roll type
- Advantage -- each die is rolled twice, the higher of the two results becomes the final result of the roll -- `adv(1d20)`
- Disadvantage -- each die is rolled twice, the lower of the two results becomes the final result of the roll -- `dis(1d20)`

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES (TBA) -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
