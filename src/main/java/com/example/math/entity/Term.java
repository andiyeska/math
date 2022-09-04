package com.example.math.entity;

import com.example.math.utility.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class Term {
    private BigDecimal numerator;
    private BigDecimal denominator = BigDecimal.ONE;
    private Variable variable;

    public void setVariable(Variable variable) {
        this.variable = variable;
        if(numerator == null) numerator = BigDecimal.ONE;
     }

    public void setDenominator(BigDecimal denominator) {
        if(numerator == null) numerator = BigDecimal.ONE;
        if(denominator.doubleValue() < 0) {
            numerator = numerator.multiply(BigDecimal.valueOf(-1));
            denominator = denominator.multiply(BigDecimal.valueOf(-1));
        }
        this.denominator = denominator;
    }

    public boolean add(Term term) {
        boolean isCalculated = isCalculated(term);
        if(!isCalculated) return false;

        BigDecimal termNumerator = term.getNumerator();
        BigDecimal termDenominator = term.getDenominator();

        numerator = numerator.multiply(termDenominator).add(termNumerator.multiply(denominator));
        denominator = denominator.multiply(termDenominator);

        return true;
    }

    public boolean subtract(Term term) {
        boolean isCalculated = isCalculated(term);
        if(!isCalculated) return false;

        BigDecimal termNumerator = term.getNumerator();
        BigDecimal termDenominator = term.getDenominator();

        numerator = numerator.multiply(termDenominator).subtract(termNumerator.multiply(denominator));
        denominator = denominator.multiply(termDenominator);

        return true;
    }

    public void multiply(Term term) {
        if(variable != null && term.getVariable() != null) variable.multiply(term.getVariable());
        else if(term.getVariable() != null) variable = term.getVariable();
        multiply(term.getNumerator(), term.getDenominator());
    }

    private void multiply(BigDecimal termNumerator, BigDecimal termDenominator) {
       numerator =  numerator.multiply(termNumerator);
       denominator = denominator.multiply(termDenominator);
        if(denominator.intValue() < 0) {
            numerator = numerator.multiply(BigDecimal.valueOf(-1));
            denominator = denominator.multiply(BigDecimal.valueOf(-1));
        }
    }

    public void divide(Term term) {
        if(variable != null && term.getVariable() != null) variable.divide(term.getVariable());
        else if(term.getVariable() != null) variable = term.getVariable().swap();
        multiply(term.getDenominator(), term.getNumerator());
    }

    private boolean isCalculated(Term term) {
        Variable termVariable = term.getVariable();
        if(this.variable != null && termVariable != null && !this.variable.equals(termVariable)) return false;
        else if(this.variable == null ^ termVariable == null) return false;

        return true;
    }

    private double getDecimal() {
        if(numerator == null) return 0.0;
        return numerator.divide(denominator, MathContext.DECIMAL128).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private void simplifyFraction() {
        int denominator = this.denominator.intValue();
        if(denominator > 1) {
            boolean isNegative = false;
            int divisor = 2;
            int numerator = this.numerator.intValue();
            if(numerator < 0) {
                isNegative = true;
                numerator *= -1;
            }

            while (divisor <= numerator && divisor <= denominator) {
                if(numerator % divisor == 0 && denominator % divisor == 0) {
                    numerator /= divisor;
                    denominator /= divisor;
                } else {
                    divisor += 1;
                }
            }

            if(isNegative) numerator *= -1;

            this.numerator = BigDecimal.valueOf(numerator);
            this.denominator = BigDecimal.valueOf(denominator);
        }
    }

    public String printFraction() {
        simplifyFraction();
        Map<Character, Integer> numeratorLetters = new HashMap<>();
        Map<Character, Integer> denominatorLetters = new HashMap<>();
        if(variable != null) {
            numeratorLetters = variable.getNumeratorLetters();
            denominatorLetters = variable.getDenominatorLetters();
        }

        String numeratorString = printCoefficientVariable(numerator, numeratorLetters);
        if(numeratorString.isEmpty()) numeratorString = String.valueOf(BigDecimal.ONE.intValue());
        if(numeratorString.equals("0")) return numeratorString;

        String denominatorString = printCoefficientVariable(denominator, denominatorLetters);
        if(StringUtil.isNotNullAndNotEmpty(denominatorString)) denominatorString = "/" + denominatorString;

        return numeratorString + denominatorString;
    }

    public String printCoefficientVariable(BigDecimal coefficient, Map<Character, Integer> letters) {
        if(coefficient.compareTo(BigDecimal.ZERO) == 0) return "0";
        if(coefficient.compareTo(BigDecimal.ONE) == 0) {
            if(letters.isEmpty()) return "";
            else return Variable.printLetters(letters);
        }
        if(coefficient.compareTo(BigDecimal.valueOf(-1)) == 0) {
            if(letters.isEmpty()) return String.valueOf(coefficient.intValue());
            else return "-" + Variable.printLetters(letters);
        }
        return coefficient.intValue() + Variable.printLetters(letters);
    }

    public String printDecimal() {
        double decimal = getDecimal();
        String decimalString = BigDecimal.valueOf(decimal).stripTrailingZeros().toPlainString();
        String variableString = variable == null? "" : variable.printVariable();
        if(decimalString.equals("0")) variableString = "";
        else if((decimalString.equals("1") || decimalString.equals("-1")) && !variableString.isEmpty() && Character.isLetter(variableString.charAt(0)))
            decimalString = "";
        return decimalString + variableString;
    }

    public static String printTerms(List<Term> terms) {
        String termsString = "";
        for (Term leftTerm : terms) {
            if(leftTerm.getNumerator().intValue() >= 0) termsString += "+";
            termsString += leftTerm.printFraction();
        }
        if(!termsString.isEmpty() && termsString.charAt(0) == '+') termsString = termsString.substring(1);

        return  termsString;
    }

    @Override
    public String toString() {
        return printFraction();
    }
}
