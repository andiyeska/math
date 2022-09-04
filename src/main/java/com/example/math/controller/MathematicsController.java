package com.example.math.controller;

import com.example.math.service.equation.qudratic.GetEquationQuadraticNoneInputBoundary;
import com.example.math.service.equation.qudratic.none.GetEquationQuadraticNoneRequest;
import com.example.math.service.equation.qudratic.none.GetEquationQuadraticNoneResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MathematicsController {

    private final GetEquationQuadraticNoneInputBoundary getEquationQuadraticNoneUC;

    @PostMapping(value = "/mathematics/equation/quadratic/none")
    private GetEquationQuadraticNoneResponse getEquationQuadraticNoneSolution(@RequestBody GetEquationQuadraticNoneRequest request) throws Exception {
        return getEquationQuadraticNoneUC.getSolution(request);
    }

}
