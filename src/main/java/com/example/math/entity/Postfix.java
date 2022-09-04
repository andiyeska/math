package com.example.math.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.*;

@Data
public class Postfix {
    private Map<Integer, Term> termBySeq = new HashMap<>();
    private Map<Integer, Operator> operatorBySeq = new HashMap<>();

    public Postfix(Map<Integer, Term> termBySeq, Map<Integer, Operator> operatorBySeq) {
        this.termBySeq = termBySeq;
        this.operatorBySeq = operatorBySeq;
    }

    public Postfix(String equation) throws Exception {
        Stack<Operator> stackedOperators = new Stack<>();
        boolean isNegative = false;
        int seq = 1;
        String digitString = "";
        Variable variable = null;
        for (int i = 0; i < equation.length(); i++) {
            Character character = equation.charAt(i);

            if(Character.isDigit(character)) {
                digitString += character;
                continue;
            } else if(Character.isLetter(character)) {
                if(variable == null) variable = new Variable(character);
                else variable.addLetter(character);
                continue;
            }

            Term term = getTerm(isNegative, digitString, variable);
            if(!digitString.isEmpty() || variable != null) {
                termBySeq.put(seq, term);
                seq += 1;
                variable = null;
                digitString = "";
                isNegative = false;
            }

            Operator operator = Operator.getOperatorFromSignChar(character);
            Character prevCharacter = (i - 1) < 0 ? null : equation.charAt(i - 1);
            if(operator == Operator.START_PARENTHESIS) {
                if(prevCharacter != null && (Character.isLetter(prevCharacter) || Character.isDigit(prevCharacter) || prevCharacter == ')'))
                    stackedOperators.push(Operator.MULTIPLY);
                stackedOperators.push(operator);
                continue;
            }

            if(operator == Operator.END_PARENTHESIS) {
                while (!stackedOperators.isEmpty()) {
                    Operator peekedOperator = stackedOperators.peek();
                    if (peekedOperator == null || peekedOperator == Operator.START_PARENTHESIS) break;
                    operatorBySeq.put(seq, peekedOperator);
                    seq += 1;
                    stackedOperators.pop();
                }
                stackedOperators.pop();
                continue;
            }

            if(operator == Operator.SUBTRACT) {
                if(prevCharacter != null && !Character.isDigit(prevCharacter) && !Character.isLetter(prevCharacter) && prevCharacter != ')') {
                    isNegative = !isNegative;
                    continue;
                }
            }

            while (!stackedOperators.isEmpty()) {
                Operator peekedOperator = stackedOperators.peek();
                if(operator.getPrecedence() > peekedOperator.getPrecedence()) break;
                operatorBySeq.put(seq, peekedOperator);
                seq += 1;
                stackedOperators.pop();
            }
            stackedOperators.push(operator);

        }

        if(!digitString.isEmpty() || variable != null) {
            Term term = getTerm(isNegative, digitString, variable);
            termBySeq.put(seq, term);
            seq += 1;
        }

        while (!stackedOperators.isEmpty()) {
            Operator peekedOperator = stackedOperators.peek();
            if(peekedOperator == Operator.START_PARENTHESIS) throw new Exception("Invalid Equation");
            operatorBySeq.put(seq, peekedOperator);
            seq += 1;
            stackedOperators.pop();
        }
    }

    private Term getTerm(boolean isNegative, String digitString, Variable variable) {
        if(digitString.isEmpty() && variable == null) return null;
        BigDecimal numerator = BigDecimal.ONE;
        if(isNegative) numerator = numerator.multiply(BigDecimal.valueOf(-1));
        if(!digitString.isEmpty()) numerator = numerator.multiply(BigDecimal.valueOf(Integer.parseInt(digitString)));

        Term term = new Term();
        term.setNumerator(numerator);
        term.setVariable(variable);

        return term;
    }

    public Solution getSolution() {
        Solution solution = new Solution();
        int maxSeq = termBySeq.size() + operatorBySeq.size();
        Stack<List<Term>> stackedTerms = new Stack<>();

        for (int i = 1; i <= maxSeq; i++) {
            if(termBySeq.containsKey(i)) {
                stackedTerms.push(Arrays.asList(termBySeq.get(i)));
            } else {
                Operator operator = operatorBySeq.get(i);
                Step step = new Step();
                Operation operation = new Operation(operator);
                operation.setRightTerms(stackedTerms.pop());
                if(!stackedTerms.isEmpty()) operation.setLeftTerms(stackedTerms.pop());
                step.setLeftOperationBefore(operation.printFraction());

                List<Term> calculatedTerms = operation.calculate();
                step.setLeftOperationAfter(Term.printTerms(calculatedTerms));

                constructStepDescription(operator, step);

                if(!step.getLeftOperationBefore().equals(step.getLeftOperationAfter())) solution.addStep(step);
                stackedTerms.push(calculatedTerms);
            }
        }

        if(!stackedTerms.isEmpty()) solution.setLeftTerms(stackedTerms.pop());
        solution.constructEquation();
        return solution;
    }

    private void constructStepDescription(Operator operator, Step step) {
        switch (operator) {
            case MULTIPLY:
            case DIVIDE:
                step.setDescription("Distribute");;
                break;
            case ADD:
            case SUBTRACT:
                step.setDescription("Simplify");
                break;
        }
    }

    @Override
    public String toString() {
        String result = "";
        int totalSeq = termBySeq.size() + operatorBySeq.size();
        for (int i = 1; i <= totalSeq; i++) {
            if(termBySeq.containsKey(i)) result += termBySeq.get(i).printFraction();
            else result += operatorBySeq.get(i).getOperatorSign();
        }

        return result;
    }

    public String printPostfixString() {
        return toString();
    }

}
