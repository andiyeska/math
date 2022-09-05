package com.example.math.service.equation.qudratic.non;

import lombok.Data;

@Data
public class GetEquationQuadraticNonRequest {
    private String equation;

    public void setEquation(String equation) {
        this.equation = equation.replaceAll("\\s+","");
    }

}
