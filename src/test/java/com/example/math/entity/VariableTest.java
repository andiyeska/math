package com.example.math.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VariableTest {

    private final Character LETTER_X = 'x';
    private final Character LETTER_Y = 'y';

    Variable variable;

    @BeforeEach
    void init() {
        variable = new Variable(LETTER_X);
    }

    @Test
    void givenLetter_whenAddLetter_shouldReturnLetterWithOneCount() {

        Assertions.assertTrue(variable.getNumeratorLetters().containsKey(LETTER_X));
        Assertions.assertEquals(1, variable.getNumeratorLetters().get(LETTER_X));
    }

    @Test
    void givenTwoSameLetter_whenAddLetter_shouldReturnLetterWithTwoCount() {
        variable.addLetter(LETTER_X);

        Assertions.assertTrue(variable.getNumeratorLetters().containsKey(LETTER_X));
        Assertions.assertEquals(2, variable.getNumeratorLetters().get(LETTER_X));
    }

    @Test
    void givenLetter_whenPrintVariable_shouldReturnLetter() {
        Assertions.assertEquals("x", variable.printVariable());
    }

    @Test
    void givenTwoLetter_whenPrintVariable_shouldReturnLetterWithExponent() {
        variable.addLetter(LETTER_X);

        Assertions.assertEquals("x^2", variable.printVariable());
    }

    @Test
    void givenDifferentVariable_whenEquals_shouldReturnFalse() {
        Variable variable2 = new Variable(LETTER_Y);

        Assertions.assertFalse(variable.equals(variable2));
    }

    @Test
    void givenEqualVariable_whenEquals_shouldReturnTrue() {
        Variable variable2 = new Variable(LETTER_X);

        Assertions.assertTrue(variable.equals(variable2));
    }

    @Test
    void givenNumeratorWithSameLetter_whenDivide_shouldReturnCorrectResult() {
        Variable variable2 = new Variable(LETTER_X);

        variable.addLetter(LETTER_X);
        variable.divide(variable2);

        Assertions.assertEquals("x", variable.printVariable());
    }

    @Test
    void givenNumeratorWithSameLetterAndCount_whenDivide_shouldReturnCorrectResult() {
        Variable variable2 = new Variable(LETTER_X);
        variable2.addLetter(LETTER_X);

        variable.addLetter(LETTER_X);
        variable.divide(variable2);

        Assertions.assertEquals("", variable.printVariable());
    }

    @Test
    void givenNumeratorWithDifferentLetterAndSameCount_whenDivide_shouldReturnCorrectResult() {
        Variable variable2 = new Variable(LETTER_Y);
        variable2.addLetter(LETTER_Y);

        variable.addLetter(LETTER_X);
        variable.divide(variable2);

        Assertions.assertEquals("x^2/y^2", variable.printVariable());
    }

    @Test
    void givenNumeratorAndFractionVariable_whenDivide_shouldReturnCorrectResult() {
        Variable variable2 = new Variable(LETTER_Y);
        variable2.addLetter(LETTER_Y);

        Variable variable3 = new Variable(LETTER_X);
        variable3.addLetter(LETTER_X);

        variable.addLetter(LETTER_X);
        variable.divide(variable2);
        variable.divide(variable3);

        Assertions.assertEquals("/y^2", variable.printVariable());
    }


    @Test
    void givenNumeratorWithSameLetter_whenMultiply_shouldReturnCorrectResult() {
        Variable variable2 = new Variable(LETTER_X);
        variable2.addLetter(LETTER_X);

        variable.multiply(variable2);

        Assertions.assertEquals("x^3", variable.printVariable());
    }

    @Test
    void givenNumeratorWithDifferentLetter_whenMultiply_shouldReturnCorrectResult() {
        Variable variable2 = new Variable(LETTER_Y);

        variable.addLetter(LETTER_X);
        variable.multiply(variable2);

        Assertions.assertEquals("x^2y", variable.printVariable());
    }

    @Test
    void givenFraction_whenMultiply_shouldReturnCorrectResult() {
        Variable variable2 = new Variable(LETTER_X);

        Variable variable3 = new Variable(LETTER_Y);
        variable3.divide(variable2);

        Variable variable4 = new Variable(LETTER_Y);
        variable4.addLetter(LETTER_Y);

        variable2.addLetter(LETTER_Y);
        variable.addLetter(LETTER_X);
        variable.divide(variable4);
        variable.multiply(variable3);

        Assertions.assertEquals("x/y", variable.printVariable());
    }

    @Test
    void givenFraction_whenSwap_shouldBeSwapper() {
        Variable variable2 = new Variable(LETTER_Y);
        variable.divide(variable2);
        variable.swap();

        Assertions.assertTrue(variable.getNumeratorLetters().containsKey(LETTER_Y));
        Assertions.assertTrue(variable.getDenominatorLetters().containsKey(LETTER_X));
    }

}
