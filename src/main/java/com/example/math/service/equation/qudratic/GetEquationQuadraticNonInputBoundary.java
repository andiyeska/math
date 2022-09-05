package com.example.math.service.equation.qudratic;

import com.example.math.service.equation.qudratic.non.GetEquationQuadraticNonRequest;
import com.example.math.service.equation.qudratic.non.GetEquationQuadraticNonResponse;

public interface GetEquationQuadraticNonInputBoundary {

    GetEquationQuadraticNonResponse getSolution(GetEquationQuadraticNonRequest request) throws Exception;

}
