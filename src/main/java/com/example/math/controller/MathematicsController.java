package com.example.math.controller;

import com.example.math.service.equation.qudratic.GetEquationQuadraticNonInputBoundary;
import com.example.math.service.equation.qudratic.non.GetEquationQuadraticNonRequest;
import com.example.math.service.equation.qudratic.non.GetEquationQuadraticNonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MathematicsController {

    private final GetEquationQuadraticNonInputBoundary getEquationQuadraticNonUC;

    @PostMapping(value = "/mathematics/equation/quadratic/non")
    private GetEquationQuadraticNonResponse getEquationQuadraticNonSolution(@RequestBody GetEquationQuadraticNonRequest request) throws Exception {
        return getEquationQuadraticNonUC.getSolution(request);
    }

}
