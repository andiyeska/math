package com.example.math.entity;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class Operation {
    private List<Term> leftTerms = new ArrayList<>();
    private Operator operator;
    private List<Term> rightTerms = new ArrayList<>();

    public Operation(Operator operator) {
        this.operator = operator;
    }

    public Operation(List<Term> leftTerms, Operator operator, List<Term> rightTerms) {
        this.leftTerms = leftTerms;
        this.operator = operator;
        this.rightTerms = rightTerms;
    }

    public void addLeftTerm(Term term) {
        leftTerms.add(term);
    }

    public void addRightTerm(Term term) {
        rightTerms.add(term);
    }

    public List<Term> calculate() {
        switch (operator) {
            case MULTIPLY:
                return doMultiplication();
            case DIVIDE:
                return doDivision();
            case SUBTRACT:
                simplifySubtract();
            case ADD:
            default:
                return doAddition();
        }
    }

    private List<Term> doAddition() {
        List<Term> result = Stream.of(leftTerms, rightTerms).flatMap(Collection::stream).collect(Collectors.toList());
        List<Term> calculatedTerms = new ArrayList<>(result);
        while (calculatedTerms.size() > 0) {
            calculatedTerms = new ArrayList<>();
            List<Term> evaluatedTerms = new ArrayList<>(result);
            result = new ArrayList<>();

            for (int i = 0; i < evaluatedTerms.size(); i++) {
                Term term1 = evaluatedTerms.get(i);
                if(term1 == null) continue;
                for (int j = i+1; j < evaluatedTerms.size(); j++) {
                    Term term2 = evaluatedTerms.get(j);
                    if(term2 == null) continue;
                    boolean isCalculated = term1.add(term2);

                    if(isCalculated) {
                        calculatedTerms.add(term1);
                        evaluatedTerms.set(i, null);
                        evaluatedTerms.set(j, null);
                        break;
                    }
                }

                if(term1.getNumerator().intValue() != 0) result.add(term1);
            }

        }
        return result;
    }

    private List<Term> doDivision() {
        List<Term> result = new ArrayList<>();
        for (Term leftTerm : leftTerms) {
            for (Term rightTerm : rightTerms) {
                Term dividedTerm = new Term();
                BeanUtils.copyProperties(leftTerm, dividedTerm);
                dividedTerm.divide(rightTerm);
                result.add(dividedTerm);
            }
        }
        return result;
    }

    private List<Term> doMultiplication() {
        List<Term> result = new ArrayList<>();
        for (Term leftTerm : leftTerms) {
            for (Term rightTerm : rightTerms) {
                Term multipliedTerm = new Term();
                BeanUtils.copyProperties(leftTerm, multipliedTerm);
                multipliedTerm.multiply(rightTerm);
                result.add(multipliedTerm);
            }
        }
        return result;
    }

    private void simplifySubtract() {
        operator = Operator.ADD;
        for (Term rightTerm : rightTerms) {
            rightTerm.setNumerator(rightTerm.getNumerator().multiply(BigDecimal.valueOf(-1)));
        }
    }

    public String printFraction() {
        String leftTermString = Term.printTerms(leftTerms);
        String rightTermString = Term.printTerms(rightTerms);

        return printOperation(leftTermString, rightTermString);
    }

    private String printOperation(String leftTermString, String rightTermString) {
        String operatorSign = operator.getOperatorSign();
        if(operator == Operator.ADD && !rightTermString.isEmpty() && rightTermString.charAt(0) == '-') operatorSign = "";
        if(operator != Operator.ADD && leftTerms.size() > 1) leftTermString = String.format("(%s)", leftTermString);
        if(operator != Operator.ADD && rightTerms.size() > 1) rightTermString = String.format("(%s)", rightTermString);

        if(operator == Operator.MULTIPLY && (leftTerms.size() > 1 || rightTerms.size() > 1)) operatorSign = "";

        return leftTermString + operatorSign + rightTermString;
    }

    @Override
    public String toString() {
        return printFraction();
    }
}
