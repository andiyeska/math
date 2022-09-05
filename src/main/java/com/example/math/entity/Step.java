package com.example.math.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Step {
    private String description;
    private String leftEquationBefore = "0";
    private String leftEquationAfter = "0";
    private String rightEquationBefore = "0";
    private String rightEquationAfter = "0";
    private String leftOperationBefore;
    private String leftOperationAfter;
    private String rightOperationBefore;
    private String rightOperationAfter;

    public String printEquationBefore() {
        return leftEquationBefore + "=" + rightEquationBefore;
    }

}
