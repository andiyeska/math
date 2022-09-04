package com.example.math.service.equation.qudratic.none;

import lombok.Data;

@Data
public class GetEquationQuadraticNoneRequest {
    private String equation;

    public void setEquation(String equation) {
        this.equation = equation.replaceAll("\\s+","");
    }

}
