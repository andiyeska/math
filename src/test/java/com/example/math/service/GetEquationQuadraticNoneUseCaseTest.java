package com.example.math.service;

import com.example.math.entity.Solution;
import com.example.math.service.equation.qudratic.none.GetEquationQuadraticNoneRequest;
import com.example.math.service.equation.qudratic.none.GetEquationQuadraticNoneUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetEquationQuadraticNoneUseCaseTest {

    GetEquationQuadraticNoneUseCase useCase;
    GetEquationQuadraticNoneRequest request;

    @BeforeEach
    void init() {
        useCase = new GetEquationQuadraticNoneUseCase();
        request = new GetEquationQuadraticNoneRequest();
    }

    @Test
    void givenNoEquation_whenGetSolution_shouldThrowException() {
        Exception e = Assertions.assertThrows(Exception.class, () -> useCase.getSolution(request));
        Assertions.assertEquals("Equation cannot be null or empty", e.getMessage());
    }

    @Test
    void givenNoVariableEquation_whenGetSolution_shouldThrowException() {
        request.setEquation("5=0");
        Exception e = Assertions.assertThrows(Exception.class, () -> useCase.getSolution(request));
        Assertions.assertEquals("Equation has no variable after calculation", e.getMessage());
    }

    @Test
    void givenEquation_whenGetSolution_shouldReturnCorrectSolution() throws Exception {
        request.setEquation("2(4x+3)+6=24-4x");
        Solution solution = useCase.getSolution(request).getSolution();

        Assertions.assertEquals("1", solution.getTermByVariable().get("x").printFraction());
    }

    @Test
    void givenEquationWithSpace_whenGetSolution_shouldReturnCorrectSolution() throws Exception {
        request.setEquation("7x - 2 = 21");
        Solution solution = useCase.getSolution(request).getSolution();

        Assertions.assertEquals("23/7", solution.getTermByVariable().get("x").printFraction());
    }

    @Test
    void givenEquationWithDenominator_whenGetSolution_shouldReturnCorrectSolution() throws Exception {
        request.setEquation("x/2+3=x");
        Solution solution = useCase.getSolution(request).getSolution();

        Assertions.assertEquals("6", solution.getTermByVariable().get("x").printFraction());
    }

}
