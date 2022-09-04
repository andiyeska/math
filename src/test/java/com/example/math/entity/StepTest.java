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
    void givenLeftAndRightEquation_whenPrintEquation_shouldReturnCorrectResult() {
        step.setLeftEquation("a");
        step.setRightEquation("1");

        Assertions.assertEquals("a=1", step.printEquation());
    }

}
