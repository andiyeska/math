package com.example.math.service.equation.qudratic;

import com.example.math.service.equation.qudratic.none.GetEquationQuadraticNoneRequest;
import com.example.math.service.equation.qudratic.none.GetEquationQuadraticNoneResponse;

public interface GetEquationQuadraticNoneInputBoundary {

    GetEquationQuadraticNoneResponse getSolution(GetEquationQuadraticNoneRequest request) throws Exception;

}
