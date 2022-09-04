package com.example.math.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class TermTest {

    final Character LETTER_X = 'x';
    final Character LETTER_Y = 'y';

    Term term;
    Variable variable;

    @BeforeEach
    void init() {
        term = new Term();
        variable = new Variable(LETTER_X);
    }

    @Test
    void givenLetter_whenAddVariable_shouldSaveLetterOnVariableAndNumerator() {
        term.setVariable(variable);

        Assertions.assertEquals(0, term.getNumerator().compareTo(BigDecimal.ONE));
        Assertions.assertTrue(term.getVariable().getNumeratorLetters().containsKey(LETTER_X));
    }

    @Test
    void givenNumber_whenSetDenominator_shouldSaveDenominatorAndNumerator() {
        term.setDenominator(BigDecimal.TEN);

        Assertions.assertEquals(0, term.getNumerator().compareTo(BigDecimal.ONE));
        Assertions.assertEquals(0, term.getDenominator().compareTo(BigDecimal.TEN));
    }

    @Test
    void givenNothing_whenPrintDecimal_shouldPrintZero() {
        Assertions.assertEquals("0", term.printDecimal());
    }

    @Test
    void givenNumerator_whenPrintDecimal_shouldPrintNumerator() {
        term.setNumerator(BigDecimal.TEN);

        Assertions.assertEquals("10", term.printDecimal());
    }

    @Test
    void givenDenominator_whenPrintDecimal_shouldPrintPointOne() {
        term.setDenominator(BigDecimal.TEN);

        Assertions.assertEquals("0.1", term.printDecimal());
    }

    @Test
    void givenNumeratorAndDenominator_whenPrintDecimal_shouldPrintOne() {
        term.setNumerator(BigDecimal.TEN);
        term.setDenominator(BigDecimal.TEN);

        Assertions.assertEquals("1", term.printDecimal());
    }

    @Test
    void givenNumeratorAndVariable_whenPrintDecimal_shouldPrintNumeratorAndVariable() {
        term.setNumerator(BigDecimal.TEN);
        term.setVariable(variable);

        Assertions.assertEquals("10x", term.printDecimal());
    }

    @Test
    void givenTenNumeratorAndHundredDenominator_whenPrintFraction_shouldReturnOneOverTen() {
        term.setNumerator(BigDecimal.TEN);
        term.setDenominator(BigDecimal.valueOf(100));

        Assertions.assertEquals("1/10", term.printFraction());
    }

    @Test
    void givenFractionNumberWithNumeratorVariable_whenPrintFraction_shouldReturnCorrectResult() {
        term.setNumerator(BigDecimal.TEN);
        term.setDenominator(BigDecimal.valueOf(100));
        term.setVariable(variable);

        Assertions.assertEquals("x/10", term.printFraction());
    }

    @Test
    void givenFractionNumberWithFractionVariable_whenPrintFraction_shouldReturnCorrectResult() {
        term.setNumerator(BigDecimal.valueOf(3));
        term.setDenominator(BigDecimal.valueOf(100));

        Variable variable2 = new Variable(LETTER_Y);
        variable2.addLetter(LETTER_Y);
        variable.divide(variable2);
        term.setVariable(variable);

        Assertions.assertEquals("3x/100y^2", term.printFraction());
    }

    @Test
    void givenTwoWholeNumbers_whenAdd_shouldReturnCorrectCalculation() {
        term.setNumerator(BigDecimal.ONE);
        Term term2 = new Term();
        term2.setNumerator(BigDecimal.ONE);
        boolean equalVariable = term.add(term2);

        Assertions.assertTrue(equalVariable);
        Assertions.assertEquals("2", term.printFraction());
    }

    @Test
    void givenTwoFractions_whenAdd_shouldReturnCorrectCalculation() {
        term.setDenominator(BigDecimal.TEN);
        Term term2 = new Term();
        term2.setDenominator(BigDecimal.valueOf(5));
        boolean equalVariable = term.add(term2);

        Assertions.assertTrue(equalVariable);
        Assertions.assertEquals("3/10", term.printFraction());
    }

    @Test
    void givenTwoEqualsVariable_whenAdd_shouldReturnCorrectCalculation() {
        term.setVariable(variable);
        Term term2 = new Term();
        term2.setVariable(variable);
        boolean equalVariable = term.add(term2);

        Assertions.assertTrue(equalVariable);
        Assertions.assertEquals("2x", term.printFraction());
    }

    @Test
    void givenFractionsNumberAndTwoEqualsVariable_whenAdd_shouldReturnCorrectCalculation() {
        term.setDenominator(BigDecimal.TEN);
        term.setVariable(variable);
        Term term2 = new Term();
        term2.setDenominator(BigDecimal.valueOf(5));
        term2.setVariable(variable);
        boolean equalVariable = term.add(term2);

        Assertions.assertTrue(equalVariable);
        Assertions.assertEquals("3x/10", term.printFraction());
    }

    @Test
    void givenFractionsNumberAndFractionsVariable_whenAdd_shouldReturnCorrectCalculation() {
        Variable variable2 = new Variable(LETTER_Y);
        variable.divide(variable2);;

        term.setDenominator(BigDecimal.TEN);
        term.setVariable(variable);
        Term term2 = new Term();
        term2.setDenominator(BigDecimal.valueOf(5));
        term2.setVariable(variable);
        boolean equalVariable = term.add(term2);

        Assertions.assertTrue(equalVariable);
        Assertions.assertEquals("3x/10y", term.printFraction());
    }

    @Test
    void givenDifferentVariable_whenAdd_shouldNotCalculate() {
        term.setVariable(variable);
        Term term2 = new Term();
        term2.setNumerator(BigDecimal.ONE);
        boolean equalVariable = term.add(term2);

        Assertions.assertFalse(equalVariable);
        Assertions.assertEquals("x", term.printFraction());
    }

    @Test
    void givenDifferentFractionVariable_whenAdd_shouldNotCalculate() {
        Variable variable2 = new Variable(LETTER_Y);
        Variable variable3 = new Variable(LETTER_X);
        variable3.addLetter(LETTER_Y);

        variable.divide(variable3);
        variable2.divide(variable3);

        term.setVariable(variable2);
        Term term2 = new Term();
        term2.setVariable(variable);
        boolean equalVariable = term.add(term2);

        Assertions.assertFalse(equalVariable);
        Assertions.assertEquals("1/x", term.printFraction());
    }

    @Test
    void givenTwoWholeNumbers_whenSubtract_shouldReturnCorrectCalculation() {
        term.setNumerator(BigDecimal.ONE);
        Term term2 = new Term();
        term2.setNumerator(BigDecimal.ONE);
        boolean equalVariable = term.subtract(term2);

        Assertions.assertTrue(equalVariable);
        Assertions.assertEquals("0", term.printFraction());
    }

    @Test
    void givenTwoFractions_whenSubtract_shouldReturnCorrectCalculation() {
        term.setDenominator(BigDecimal.TEN);
        Term term2 = new Term();
        term2.setDenominator(BigDecimal.valueOf(5));
        boolean equalVariable = term.subtract(term2);

        Assertions.assertTrue(equalVariable);
        Assertions.assertEquals("-1/10", term.printFraction());
    }

    @Test
    void givenTwoEqualsVariable_whenSubtract_shouldReturnCorrectCalculation() {
        term.setNumerator(BigDecimal.TEN);
        term.setVariable(variable);
        Term term2 = new Term();
        term2.setVariable(variable);
        boolean equalVariable = term.subtract(term2);

        Assertions.assertTrue(equalVariable);
        Assertions.assertEquals("9x", term.printFraction());
    }

    @Test
    void givenFractionsNumberAndTwoEqualsVariable_whenSubtract_shouldReturnCorrectCalculation() {
        term.setDenominator(BigDecimal.TEN);
        term.setVariable(variable);
        Term term2 = new Term();
        term2.setDenominator(BigDecimal.valueOf(5));
        term2.setVariable(variable);
        boolean equalVariable = term.subtract(term2);

        Assertions.assertTrue(equalVariable);
        Assertions.assertEquals("-x/10", term.printFraction());
    }

    @Test
    void givenFractionsNumberAndFractionsVariable_whenSubtract_shouldReturnCorrectCalculation() {
        Variable variable2 = new Variable(LETTER_Y);
        variable.divide(variable2);;

        term.setDenominator(BigDecimal.TEN);
        term.setVariable(variable);
        Term term2 = new Term();
        term2.setDenominator(BigDecimal.valueOf(5));
        term2.setVariable(variable);
        boolean equalVariable = term.subtract(term2);

        Assertions.assertTrue(equalVariable);
        Assertions.assertEquals("-x/10y", term.printFraction());
    }

    @Test
    void givenDifferentVariable_whenSubtract_shouldNotCalculate() {
        term.setVariable(variable);
        Term term2 = new Term();
        term2.setNumerator(BigDecimal.ONE);
        boolean equalVariable = term.subtract(term2);

        Assertions.assertFalse(equalVariable);
        Assertions.assertEquals("x", term.printFraction());
    }

    @Test
    void givenDifferentFractionVariable_whenSubtract_shouldNotCalculate() {
        Variable variable2 = new Variable(LETTER_Y);
        Variable variable3 = new Variable(LETTER_X);
        variable3.addLetter(LETTER_Y);

        variable.divide(variable3);
        variable2.divide(variable3);

        term.setVariable(variable2);
        Term term2 = new Term();
        term2.setVariable(variable);
        boolean equalVariable = term.subtract(term2);

        Assertions.assertFalse(equalVariable);
        Assertions.assertEquals("1/x", term.printFraction());
    }

    @Test
    void givenTwoWholeNumber_whenMultiply_shouldReturnCorrectCalculation() {
        Term term2 = new Term();
        term2.setNumerator(BigDecimal.TEN);

        term.setNumerator(BigDecimal.TEN);
        term.multiply(term2);

        Assertions.assertEquals("100", term.printFraction());
    }

    @Test
    void givenTwoFractions_whenMultiply_shouldReturnCorrectCalculation() {
        Term term2 = new Term();
        term2.setNumerator(BigDecimal.ONE);
        term2.setDenominator(BigDecimal.TEN);

        term.setNumerator(BigDecimal.ONE);
        term.setDenominator(BigDecimal.TEN);
        term.multiply(term2);

        Assertions.assertEquals("1/100", term.printFraction());
    }

    @Test
    void givenNumberAndVariable_whenMultiply_shouldReturnCorrectCalculation() {
        Term term2 = new Term();
        term2.setNumerator(BigDecimal.TEN);

        term.setNumerator(BigDecimal.TEN);
        term.setVariable(variable);
        term.multiply(term2);

        Assertions.assertEquals("100x", term.printFraction());
    }

    @Test
    void givenTwoWholeNumber_whenDivide_shouldReturnCorrectCalculation() {
        Term term2 = new Term();
        term2.setNumerator(BigDecimal.TEN);

        term.setNumerator(BigDecimal.TEN);
        term.divide(term2);

        Assertions.assertEquals("1", term.printFraction());
    }

    @Test
    void givenNumberAndVariable_whenDivide_shouldReturnCorrectCalculation() {
        Term term2 = new Term();
        term2.setNumerator(BigDecimal.TEN);
        term2.setVariable(variable);

        term.setNumerator(BigDecimal.TEN);
        term.divide(term2);

        Assertions.assertEquals("1/x", term.printFraction());
    }

    @Test
    void givenNegativeDenominator_whenDivide_shouldReturnCorrectCalculation() {
        Term term2 = new Term();
        term2.setNumerator(BigDecimal.valueOf(-10));

        term.setNumerator(BigDecimal.TEN);
        term.divide(term2);

        Assertions.assertEquals("-1", term.printFraction());
    }

}
