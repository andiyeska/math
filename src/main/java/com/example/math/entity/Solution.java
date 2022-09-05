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
    private Map<String, Term> termByVariable = new HashMap<>();

    public void addTermOfVariable(String variable, Term term) {
        termByVariable.put(variable, term);
    }

    public void addStep(Step step) {
        steps.add(step);
    }

    public void constructEquation() {
        equation = Term.printTerms(leftTerms);
        if(!steps.isEmpty()) {
            for (int i = steps.size() - 1; i >= 0; i--) {
                Step step = steps.get(i);
                step.setLeftEquationAfter(equation);
                equation = equation.replace(step.getLeftOperationAfter(), step.getLeftOperationBefore());
                step.setLeftEquationBefore(equation);
            }
        }
    }

    public void merge(Solution rightSolution) {
        equation += "=" + rightSolution.getEquation();
        rightTerms = rightSolution.getLeftTerms();

        String rightEquation = rightSolution.getEquation();
        if(!rightSolution.getSteps().isEmpty()) rightEquation = rightSolution.getSteps().get(0).getLeftEquationBefore();
        for (Step step : steps) {
            step.setRightEquationBefore(rightEquation);
            step.setRightEquationAfter(rightEquation);
        }
        String leftEquation = "0";
        if(!steps.isEmpty()) {
            Step step = steps.get(steps.size() - 1);
            leftEquation = step.getLeftEquationBefore().replace(step.getLeftOperationBefore(), step.getLeftOperationAfter());
        }

        for (Step step : rightSolution.getSteps()) {
            step.setRightEquationBefore(step.getLeftEquationBefore());
            step.setRightEquationAfter(step.getLeftEquationAfter());
            step.setLeftEquationBefore(leftEquation);
            step.setLeftEquationAfter(leftEquation);
            step.setRightOperationBefore(step.getLeftOperationBefore());
            step.setRightOperationAfter(step.getLeftOperationAfter());
            step.setLeftOperationBefore("");
            step.setLeftOperationAfter("");
            steps.add(step);
        }
    }

}
