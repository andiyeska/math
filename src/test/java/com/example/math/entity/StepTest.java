package com.example.math.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StepTest {

    Step step;

    @BeforeEach
    void init() {
        step = new Step();
    }

    @Test
    void givenLeftAndRightEquation_whenPrintEquationBefore_shouldReturnCorrectResult() {
        step.setLeftEquationBefore("a");
        step.setRightEquationBefore("1");

        Assertions.assertEquals("a=1", step.printEquationBefore());
    }

}
