package com.example.math.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PostfixTest {

    @BeforeEach
    void init() {
        
    }

    @Test
    void givenEquation_whenConvertEquation_shouldReturnCorrectResult() throws Exception {
        Postfix postfix = new Postfix("2(4x+3)-6");
        Assertions.assertEquals("24x3+*6-", postfix.printPostfixString());
    }

    @Test
    void givenNegativeFirstTermEquation_whenConvertEquation_shouldReturnCorrectResult() throws Exception {
        Postfix postfix = new Postfix("-2+6");
        Assertions.assertEquals("2-6+", postfix.printPostfixString());
    }

    @Test
    void givenNegativeTermOnMultiplicationEquation_whenConvertEquation_shouldReturnCorrectResult() throws Exception {
        Postfix postfix = new Postfix("(-6)--2");
        Assertions.assertEquals("-6-2-", postfix.printPostfixString());
    }

    @Test
    void givenNegativeTermOnSubtractionEquation_whenConvertEquation_shouldReturnCorrectResult() throws Exception {
        Postfix postfix = new Postfix("2--6");
        Assertions.assertEquals("2-6-", postfix.printPostfixString());
    }

    @Test
    void givenNegativeTermOnSubtractionEquationAtFirstTerm_whenConvertEquation_shouldReturnCorrectResult() throws Exception {
        Postfix postfix = new Postfix("--2+6");
        Assertions.assertEquals("-2-6+", postfix.printPostfixString());
    }

    @Test
    void givenEquation_whenSimplify_shouldReturnCorrectSolution() throws Exception {
        Postfix postfix = new Postfix("2(4x+3)+6");
        Solution solution = postfix.getSolution();

        Assertions.assertEquals("2(4x+3)+6", solution.getEquation());
        Assertions.assertEquals(2, solution.getSteps().size());
        Assertions.assertEquals("2(4x+3)", solution.getSteps().get(0).getLeftOperationBefore());
        Assertions.assertEquals("8x+6", solution.getSteps().get(0).getLeftOperationAfter());
        Assertions.assertEquals("8x+6+6", solution.getSteps().get(1).getLeftOperationBefore());
        Assertions.assertEquals("8x+12", solution.getSteps().get(1).getLeftOperationAfter());
    }
    
}
