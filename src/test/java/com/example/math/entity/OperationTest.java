package com.example.math.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

public class OperationTest {

    Character LETTER_X = 'x';

    Operation operation;
    Term term1;
    Term term2;

    @BeforeEach
    void init() {
        term1 = new Term();
        term2 = new Term();
    }

    @Test
    void givenOperatorMultiplyOneTerm_whenPrintFraction_shouldReturnCorrectResult() {
        term1.setNumerator(BigDecimal.ONE);
        term2.setNumerator(BigDecimal.TEN);

        operation = new Operation(Operator.MULTIPLY);
        operation.addLeftTerm(term1);
        operation.addRightTerm(term2);

        Assertions.assertEquals("1*10", operation.printFraction());
    }

    @Test
    void givenOperatorMultiplyTwoTerms_whenPrintFraction_shouldReturnCorrectResult() {
        term1.setNumerator(BigDecimal.ONE);
        term2.setNumerator(BigDecimal.TEN);

        operation = new Operation(Operator.MULTIPLY);
        operation.addLeftTerm(term1);
        operation.addLeftTerm(term1);
        operation.addRightTerm(term2);
        operation.addRightTerm(term2);

        Assertions.assertEquals("(1+1)(10+10)", operation.printFraction());
    }

    @Test
    void givenOperatorDivideTwoTerms_whenPrintFraction_shouldReturnCorrectResult() {
        term1.setNumerator(BigDecimal.ONE);
        term2.setNumerator(BigDecimal.TEN);

        operation = new Operation(Operator.DIVIDE);
        operation.addLeftTerm(term1);
        operation.addLeftTerm(term1);
        operation.addRightTerm(term2);
        operation.addRightTerm(term2);

        Assertions.assertEquals("(1+1)/(10+10)", operation.printFraction());
    }

    @Test
    void givenOperatorAddTwoTerms_whenPrintFraction_shouldReturnCorrectResult() {
        term1.setNumerator(BigDecimal.ONE);
        term2.setNumerator(BigDecimal.TEN);

        operation = new Operation(Operator.ADD);
        operation.addLeftTerm(term1);
        operation.addLeftTerm(term1);
        operation.addRightTerm(term2);
        operation.addRightTerm(term2);

        Assertions.assertEquals("1+1+10+10", operation.printFraction());
    }

    @Test
    void givenOperatorAddNegativeRightTerm_whenPrintFraction_shouldReturnCorrectResult() {
        Term term3 = new Term();

        term1.setNumerator(BigDecimal.ONE);
        term2.setNumerator(BigDecimal.TEN);
        term3.setNumerator(BigDecimal.valueOf(-10));

        operation = new Operation(Operator.ADD);
        operation.addLeftTerm(term1);
        operation.addLeftTerm(term1);
        operation.addRightTerm(term3);
        operation.addRightTerm(term2);

        Assertions.assertEquals("1+1-10+10", operation.printFraction());
    }

    @Test
    void givenOperatorSubtractTwoTerms_whenPrintFraction_shouldReturnCorrectResult() {
        Term term3 = new Term();
        term1.setNumerator(BigDecimal.ONE);
        term2.setNumerator(BigDecimal.TEN);
        term3.setNumerator(BigDecimal.TEN);

        operation = new Operation(Operator.SUBTRACT);
        operation.addLeftTerm(term1);
        operation.addLeftTerm(term1);
        operation.addRightTerm(term2);
        operation.addRightTerm(term3);

        Assertions.assertEquals("(1+1)-(10+10)", operation.printFraction());
    }

    @Test
    void givenOperatorSubtractNegativeRightTerm_whenPrintFraction_shouldReturnCorrectResult() {
        Term term3 = new Term();

        term1.setNumerator(BigDecimal.ONE);
        term2.setNumerator(BigDecimal.TEN);
        term3.setNumerator(BigDecimal.valueOf(-10));

        operation = new Operation(Operator.SUBTRACT);
        operation.addLeftTerm(term1);
        operation.addLeftTerm(term1);
        operation.addRightTerm(term3);
        operation.addRightTerm(term2);

        Assertions.assertEquals("(1+1)-(-10+10)", operation.printFraction());
    }

    @Test
    void givenTwoTermsAndOperatorMultiply_whenCalculate_shouldReturnCorrectTerms() {
        term1.setNumerator(BigDecimal.ONE);
        term2.setNumerator(BigDecimal.TEN);
        Term term3 = new Term();
        Term term4 = new Term();
        BeanUtils.copyProperties(term1, term3);
        BeanUtils.copyProperties(term2, term4);

        operation = new Operation(Operator.MULTIPLY);
        operation.addLeftTerm(term1);
        operation.addLeftTerm(term3);
        operation.addRightTerm(term2);
        operation.addRightTerm(term4);

        Assertions.assertEquals("10+10+10+10", Term.printTerms(operation.calculate()));
    }

    @Test
    void givenFourTermsAndOperatorMultiply_whenCalculate_shouldReturnCorrectTerms() {
        term1.setNumerator(BigDecimal.ONE);
        term2.setNumerator(BigDecimal.TEN);
        Term term3 = new Term();
        Term term4 = new Term();
        BeanUtils.copyProperties(term1, term3);
        BeanUtils.copyProperties(term2, term4);

        operation = new Operation(Operator.DIVIDE);
        operation.addLeftTerm(term1);
        operation.addLeftTerm(term3);
        operation.addRightTerm(term2);
        operation.addRightTerm(term4);

        Assertions.assertEquals("1/10+1/10+1/10+1/10", Term.printTerms(operation.calculate()));
    }

    @Test
    void givenFourTermsAndOperatorDivide_whenCalculate_shouldReturnCorrectTerms() {
        term1.setNumerator(BigDecimal.ONE);
        term2.setNumerator(BigDecimal.TEN);
        Term term3 = new Term();
        Term term4 = new Term();
        BeanUtils.copyProperties(term1, term3);
        BeanUtils.copyProperties(term2, term4);

        operation = new Operation(Operator.DIVIDE);
        operation.addLeftTerm(term1);
        operation.addLeftTerm(term3);
        operation.addRightTerm(term2);
        operation.addRightTerm(term4);

        Assertions.assertEquals("1/10+1/10+1/10+1/10", Term.printTerms(operation.calculate()));
    }

    @Test
    void givenCalculatedTermsAndOperatorAdd_whenCalculate_shouldReturnCorrectTerms() {
        term1.setNumerator(BigDecimal.ONE);
        term2.setNumerator(BigDecimal.TEN);
        Term term3 = new Term();
        Term term4 = new Term();
        BeanUtils.copyProperties(term1, term3);
        BeanUtils.copyProperties(term2, term4);

        operation = new Operation(Operator.ADD);
        operation.addLeftTerm(term1);
        operation.addLeftTerm(term3);
        operation.addRightTerm(term2);
        operation.addRightTerm(term4);

        Assertions.assertEquals("22", Term.printTerms(operation.calculate()));
    }

    @Test
    void givenNonCalculatedTermsAndOperatorAdd_whenCalculate_shouldReturnCorrectTerms() {
        term1.setNumerator(BigDecimal.ONE);
        term1.setVariable(new Variable(LETTER_X));
        term2.setNumerator(BigDecimal.TEN);
        Term term3 = new Term();
        Term term4 = new Term();
        BeanUtils.copyProperties(term1, term3);
        BeanUtils.copyProperties(term2, term4);

        operation = new Operation(Operator.ADD);
        operation.addLeftTerm(term1);
        operation.addLeftTerm(term3);
        operation.addRightTerm(term2);
        operation.addRightTerm(term4);
        Assertions.assertEquals("2x+20", Term.printTerms(operation.calculate()));
    }

    @Test
    void givenCalculatedTermsAndOperatorSubtract_whenCalculate_shouldReturnCorrectTerms() {
        term1.setNumerator(BigDecimal.ONE);
        term2.setNumerator(BigDecimal.TEN);
        Term term3 = new Term();
        Term term4 = new Term();
        BeanUtils.copyProperties(term1, term3);
        BeanUtils.copyProperties(term2, term4);

        operation = new Operation(Operator.SUBTRACT);
        operation.addLeftTerm(term1);
        operation.addLeftTerm(term3);
        operation.addRightTerm(term2);
        operation.addRightTerm(term4);

        Assertions.assertEquals("-18", Term.printTerms(operation.calculate()));
    }

    @Test
    void givenNonCalculatedTermsAndOperatorSubtract_whenCalculate_shouldReturnCorrectTerms() {
        term1.setNumerator(BigDecimal.ONE);
        term1.setVariable(new Variable(LETTER_X));
        term2.setNumerator(BigDecimal.TEN);
        Term term3 = new Term();
        Term term4 = new Term();
        BeanUtils.copyProperties(term1, term3);
        BeanUtils.copyProperties(term2, term4);

        operation = new Operation(Operator.SUBTRACT);
        operation.addLeftTerm(term1);
        operation.addLeftTerm(term3);
        operation.addRightTerm(term2);
        operation.addRightTerm(term4);

        Assertions.assertEquals("2x-20", Term.printTerms(operation.calculate()));
    }

}
