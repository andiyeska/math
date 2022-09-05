package com.example.math.service.equation.qudratic.none;

import com.example.math.entity.*;
import com.example.math.service.equation.qudratic.GetEquationQuadraticNoneInputBoundary;
import com.example.math.utility.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GetEquationQuadraticNoneUseCase implements GetEquationQuadraticNoneInputBoundary {

    @Override
    public GetEquationQuadraticNoneResponse getSolution(GetEquationQuadraticNoneRequest request) throws Exception {
        if(StringUtil.isnullOrEmpty(request.getEquation())) throw new Exception("Equation cannot be null or empty");
        String[] equations = request.getEquation().split("=");
        Solution solution = getSolution(equations);
        removeDenominatorTerms(solution);
        moveDigitAndVariable(solution);
        setVariableCoefficientToOne(solution);

        return new GetEquationQuadraticNoneResponse(solution);
    }

    private void setVariableCoefficientToOne(Solution solution) throws Exception {
        if(solution.getLeftTerms().size() <= 0) throw new Exception("Equation has no variable after calculation");
        int coefficient = solution.getLeftTerms().get(0).getNumerator().intValue();
        if(coefficient > 1 || coefficient < 0) {
            Term coefficientTerm = new Term();
            coefficientTerm.setNumerator(BigDecimal.valueOf(coefficient));
            doOperation(solution, Operator.DIVIDE, Arrays.asList(coefficientTerm), "Set variable coefficient to 1");
        }

        solution.addTermOfVariable(solution.getLeftTerms().get(0).printDecimal(), solution.getRightTerms().get(0));
    }

    private void moveDigitAndVariable(Solution solution) {
        List<Term> negatedTerms = getNegatedTerms(solution.getLeftTerms(), solution.getRightTerms());
        if(negatedTerms.size() > 0) doOperation(solution, Operator.ADD, negatedTerms, "Move terms with variable to left and digit to right");
    }

    private void doOperation(Solution solution, Operator operator, List<Term> rightTerms, String description) {
        Operation leftOperation = new Operation(solution.getLeftTerms(), operator, rightTerms);
        Operation rightOperation = new Operation(solution.getRightTerms(), operator, rightTerms);

        Step step = new Step();
        step.setDescription(description);
        step.setLeftEquationBefore(Term.printTerms(solution.getLeftTerms()));
        step.setRightEquationBefore(Term.printTerms(solution.getRightTerms()));
        step.setLeftOperationBefore(leftOperation.printFraction());
        step.setRightOperationBefore(rightOperation.printFraction());

        solution.setLeftTerms(leftOperation.calculate());
        solution.setRightTerms(rightOperation.calculate());

        step.setLeftOperationAfter(Term.printTerms(solution.getLeftTerms()));
        step.setRightOperationAfter(Term.printTerms(solution.getRightTerms()));

        solution.addStep(step);
    }

    private List<Term> getNegatedTerms(List<Term> leftTerms, List<Term> rightTerms) {
        List<Term> negatedTerms = new ArrayList<>();
        for (Term leftTerm : leftTerms) {
            if(leftTerm.getVariable() == null || leftTerm.getVariable().isEmpty()) {
                Term negatedTerm = new Term();
                BeanUtils.copyProperties(leftTerm, negatedTerm);
                negatedTerm.setNumerator(negatedTerm.getNumerator().multiply(BigDecimal.valueOf(-1)));
                negatedTerms.add(negatedTerm);
            }
        }

        for (Term rightTerm : rightTerms) {
            if(rightTerm.getVariable() != null && !rightTerm.getVariable().isEmpty()) {
                Term negatedTerm = new Term();
                BeanUtils.copyProperties(rightTerm, negatedTerm);
                negatedTerm.setNumerator(negatedTerm.getNumerator().multiply(BigDecimal.valueOf(-1)));
                negatedTerms.add(negatedTerm);
            }
        }

        return negatedTerms;
    }

    private void removeDenominatorTerms(Solution solution) {
        List<Term> denominatorTerms = getDenominatorTerms(Stream.of(solution.getLeftTerms(), solution.getRightTerms())
                .flatMap(Collection::stream).collect(Collectors.toList()));
        if(denominatorTerms.size() > 0) doOperation(solution, Operator.MULTIPLY, denominatorTerms, "Remove Denominator");
    }

    private List<Term> getDenominatorTerms(List<Term> terms) {
        List<Term> denominatorTerms = new ArrayList<>();
        for (Term term : terms) {
            BigDecimal denominator = term.getDenominator();
            Map<Character, Integer> denominatorLetters = term.getVariable() == null ? new HashMap<>() : term.getVariable().getDenominatorLetters();
            if(denominator.intValue() > 1 || denominatorLetters.size() > 0) {
                Term denominatorTerm = new Term();
                denominatorTerm.setNumerator(denominator);
                denominatorTerm.setVariable(new Variable(denominatorLetters));
                denominatorTerms.add(denominatorTerm);
            }
        }
        return denominatorTerms;
    }

    private Solution getSolution(String[] equations) throws Exception {
        Postfix leftPostfix = new Postfix(equations[0]);
        Solution leftSolution = leftPostfix.getSolution();
        leftSolution.constructEquation();

        Postfix rightPostfix = new Postfix(equations[1]);
        Solution rightSolution = rightPostfix.getSolution();
        rightSolution.constructEquation();

        leftSolution.merge(rightSolution);
        return leftSolution;
    }


}
