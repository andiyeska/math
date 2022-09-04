package com.example.math.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class Solution {
    private String equation = "0";
    private List<Step> steps = new ArrayList<>();
    private List<Term> leftTerms = new ArrayList<>();
    private List<Term> rightTerms = new ArrayList<>();
    private Map<String, String> valueByVariable = new HashMap<>();

    public void addValueOfVariable(String variable, String value) {
        valueByVariable.put(variable, value);
    }

    public void addStep(Step step) {
        steps.add(step);
    }

    public void constructEquation() {
        equation = Term.printTerms(leftTerms);
        if(!steps.isEmpty()) {
            for (int i = steps.size() - 1; i >= 0; i--) {
                Step step = steps.get(i);
                equation = equation.replace(step.getLeftOperationAfter(), step.getLeftOperationBefore());
                step.setLeftEquation(equation);
            }
        }
    }

    public void merge(Solution rightSolution) {
        equation += "=" + rightSolution.getEquation();
        rightTerms = rightSolution.getLeftTerms();

        String rightEquation = rightSolution.getEquation();
        if(!rightSolution.getSteps().isEmpty()) rightEquation = rightSolution.getSteps().get(0).getLeftEquation();
        for (Step step : steps) {
            step.setRightEquation(rightEquation);
        }
        String leftEquation = "0";
        if(!steps.isEmpty()) {
            Step step = steps.get(steps.size() - 1);
            leftEquation = step.getLeftEquation().replace(step.getLeftOperationBefore(), step.getLeftOperationAfter());
        }

        for (Step step : rightSolution.getSteps()) {
            step.setRightEquation(step.getLeftEquation());
            step.setLeftEquation(leftEquation);
            step.setRightOperationBefore(step.getLeftOperationBefore());
            step.setRightOperationAfter(step.getLeftOperationAfter());
            step.setLeftOperationBefore("");
            step.setLeftOperationAfter("");
            steps.add(step);
        }
    }

}
