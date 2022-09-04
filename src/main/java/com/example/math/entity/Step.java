package com.example.math.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Step {
    private String description;
    private String leftEquation = "0";
    private String rightEquation = "0";
    private String leftOperationBefore;
    private String leftOperationAfter;
    private String rightOperationBefore;
    private String rightOperationAfter;

    public String printEquation() {
        return leftEquation + "=" + rightEquation;
    }

}
