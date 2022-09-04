package com.example.math.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class Variable {
    private Map<Character, Integer> numeratorLetters = new HashMap<>();
    private Map<Character, Integer> denominatorLetters = new HashMap<>();

    public Variable(Character letter) {
        addLetter(Character.toLowerCase(letter));
    }

    public Variable(Map<Character, Integer> numeratorLetters) {
        this.numeratorLetters = numeratorLetters;
    }

    public void addLetter(Character letter) {
        letter = Character.toLowerCase(letter);
        Integer letterCount = numeratorLetters.get(letter);
        if(letterCount == null) letterCount = 0;
        numeratorLetters.put(letter, letterCount + 1);
    }

    public void multiply(Variable variable) {
        multiply(variable.getNumeratorLetters(), variable.getDenominatorLetters());
    }

    public void divide(Variable variable) {
        multiply(variable.getDenominatorLetters(), variable.getNumeratorLetters());
    }

    public Variable swap() {
        Map<Character, Integer> temp = numeratorLetters;
        numeratorLetters = denominatorLetters;
        denominatorLetters = temp;

        return this;
    }

    public void multiply(Map<Character, Integer> variableNumeratorLetters, Map<Character, Integer> variableDenominatorLetters) {
        updateLetterCount(numeratorLetters, denominatorLetters, variableNumeratorLetters);
        updateLetterCount(denominatorLetters, numeratorLetters, variableDenominatorLetters);
    }

    public void updateLetterCount(Map<Character, Integer> addedLetter, Map<Character, Integer> subtractedLetter, Map<Character, Integer> variableLetter) {
        for (Map.Entry<Character, Integer> entry : variableLetter.entrySet()) {
            Character letter = entry.getKey();
            Integer letterCount = entry.getValue();

            Integer subtractedLetterCount = subtractedLetter.get(letter);
            if(subtractedLetterCount != null) {
                if (subtractedLetterCount > letterCount) {
                    subtractedLetterCount -= letterCount;
                    letterCount -= letterCount;
                } else {
                    letterCount -= subtractedLetterCount;
                    subtractedLetterCount -= subtractedLetterCount;
                }

                if(subtractedLetterCount > 0) subtractedLetter.put(letter, subtractedLetterCount);
                else subtractedLetter.remove(letter);

                if(letterCount <= 0) continue;
            }

            Integer addedLetterCount = addedLetter.get(letter);
            if(addedLetterCount == null) addedLetterCount = 0;
            addedLetter.put(letter, addedLetterCount + letterCount);
        }
    }

    public boolean equals(Variable variable) {
        Map<Character, Integer> variableNumeratorLetters = variable.getNumeratorLetters();
        if(this.numeratorLetters.size() != variableNumeratorLetters.size()) return false;
        for (Map.Entry<Character, Integer> entry : this.numeratorLetters.entrySet()) {
            Integer letterCount = variableNumeratorLetters.get(entry.getKey());
            if(letterCount == null || letterCount.compareTo(entry.getValue()) != 0) return false;
        }

        Map<Character, Integer> variableDenominatorLetters = variable.getDenominatorLetters();
        if(this.denominatorLetters.size() != variableDenominatorLetters.size()) return false;
        for (Map.Entry<Character, Integer> entry : this.denominatorLetters.entrySet()) {
            Integer letterCount = variableDenominatorLetters.get(entry.getKey());
            if(letterCount == null || letterCount.compareTo(entry.getValue()) != 0) return false;
        }

        return true;
    }

    public String printVariable() {
        String result = "";
        if(numeratorLetters.size() > 0) result += printLetters(numeratorLetters);
        if(denominatorLetters.size() > 0) result += "/" + printLetters(denominatorLetters);

        return result;
    }

    public static String printLetters(Map<Character, Integer> letters) {
        String result = "";
        for (Map.Entry<Character, Integer> entry : letters.entrySet()) {
            result += entry.getKey();
            Integer letterCount = entry.getValue();
            if (letterCount > 1) result += "^" + letterCount;
        }
        return result;
    }

    public boolean isEmpty() {
        return numeratorLetters.isEmpty() && denominatorLetters.isEmpty();
    }

    @Override
    public String toString() {
        return printVariable();
    }
}
