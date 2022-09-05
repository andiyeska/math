package com.example.math.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SolutionTest {

    Solution solution;

    @BeforeEach
    void init() {
        solution = new Solution();
    }

    Step createStep(String formulaBefore, String formulaAfter) {
        Step step = new Step();
        step.setLeftOperationBefore(formulaBefore);
        step.setLeftOperationAfter(formulaAfter);
        return step;
    }

    @Test
    void givenSteps_whenConstructEquation_shouldReturnCorrectEquation() {
        Term termA = new Term();
        termA.setVariable(new Variable('a'));

        Term termB = new Term();
        termB.setVariable(new Variable('b'));

        Term termC = new Term();
        termC.setNumerator(BigDecimal.valueOf(-2));
        termC.setVariable(new Variable('c'));

        Term termD = new Term();
        termD.setNumerator(BigDecimal.valueOf(-2));
        termD.setVariable(new Variable('d'));

        List<Step> steps = new ArrayList<>();
        steps.add(createStep("2(c+d)", "2c+2d"));
        steps.add(createStep("a+b-(2c+2d)", "a+b-2c-2d"));

        solution.setSteps(steps);
        solution.setLeftTerms(Arrays.asList(termA, termB, termC, termD));
        solution.constructEquation();

        Assertions.assertEquals("a+b-(2(c+d))", solution.getEquation());
    }

    @Test
    void givenSolution_whenMerge_shouldReturnCorrectSolution() throws Exception {
        Postfix leftPostfix = new Postfix("2(4x+3)+6");
        Solution leftSolution = leftPostfix.getSolution();
        leftSolution.constructEquation();
        Postfix rightPostfix = new Postfix("2(24-4x)");
        Solution rightSolution = rightPostfix.getSolution();
        rightSolution.constructEquation();

        leftSolution.merge(rightSolution);

        Assertions.assertEquals(3, leftSolution.getSteps().size());
        Assertions.assertEquals("2(4x+3)+6=2(24-4x)", leftSolution.getSteps().get(0).printEquationBefore());
        Assertions.assertEquals("8x+6+6=2(24-4x)", leftSolution.getSteps().get(1).printEquationBefore());
        Assertions.assertEquals("8x+12=2(24-4x)", leftSolution.getSteps().get(2).printEquationBefore());
    }

}
