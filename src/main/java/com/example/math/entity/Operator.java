package com.example.math.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Operator {
    START_PARENTHESIS("(", -1),
    END_PARENTHESIS(")", -1),
    MULTIPLY("*", 2),
    DIVIDE("/", 2),
    ADD("+", 1),
    SUBTRACT("-", 1)
    ;

    private String operatorSign;
    private int precedence;

    public static Operator getOperatorFromSignChar(Character character) {
        switch (character) {
            case '(':
                return START_PARENTHESIS;
            case ')':
                return END_PARENTHESIS;
            case '*':
                return MULTIPLY;
            case '/':
                return DIVIDE;
            case '+':
                return ADD;
            case '-':
                return SUBTRACT;
        }

        return null;
    }

}
