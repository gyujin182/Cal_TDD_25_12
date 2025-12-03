package org.example;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Calc {

    public static int run(String exp) {

        // 괄호 제거
//        exp = stripOuterBracket(exp);
        exp = stripOuterBrackets(exp);
        // 그냥 숫자만 들어올 경우 바로 리턴
        if (!exp.contains(" ")) {
            return Integer.parseInt(exp);
        }

        boolean needToMulti = exp.contains(" * ");
        boolean needToPlus = exp.contains(" + ") || exp.contains(" - ");
        boolean needToSplit = exp.contains("(") || exp.contains(")");
        boolean needToCompound = needToPlus && needToMulti;

        exp = exp.replace("- ", "+ -");

        if (needToSplit) {
            int bracketsCount = 0;
            int splitPointIndex = -1;
            if (exp.charAt(0) == '(') {
                for (int i = 0; i < exp.length(); i++) {
                    if (exp.charAt(i) == '(') {
                        bracketsCount++;
                    } else if (exp.charAt(i) == ')') {
                        bracketsCount--;
                    }
                    if (bracketsCount == 0) {
                        splitPointIndex = i;
                        break;
                    }

                    String firstExp = exp.substring(0, splitPointIndex + 1);
                    String secondExp = exp.substring(splitPointIndex + 4);

                    char operator = exp.charAt(splitPointIndex + 2);

                    exp = Calc.run(firstExp) + " " + operator + " " + secondExp;

                    return Calc.run(exp);

                }
            } if (exp.charAt(0) != '('){
                for (int j = 5; j < exp.length(); j++) {
                    if (exp.charAt(j) == '(') {
                        bracketsCount++;
                    } else if (exp.charAt(j) == ')') {
                        bracketsCount--;
                    }
                    if (bracketsCount == 0) {
                        splitPointIndex = j;
                        break;
                    }
                }

            }
            String firstExp = exp.substring(0,2);
            String secondExp = exp.substring(6, splitPointIndex);

            char operator = exp.charAt(splitPointIndex -3);

            exp = Calc.run(firstExp) + " " + operator + " " + secondExp;

            return Calc.run(exp);

        } else if (needToCompound) {
            String[] bits = exp.split(" \\+ ");

            String newExp = Arrays.stream(bits)
                    .mapToInt(Calc::run)
                    .mapToObj(e -> e + "")
                    .collect(Collectors.joining(" + "));

            return run(newExp);
        }
        if (needToPlus) {
            String[] bits = exp.split(" \\+ ");

            int sum = 0;

            for (int i = 0; i < bits.length; i++) {
                sum += Integer.parseInt(bits[i]);
            }

            return sum;
        } else if (needToMulti) {
            String[] bits = exp.split(" \\* ");

            int sum = 1;

            for (int i = 0; i < bits.length; i++) {
                sum *= Integer.parseInt(bits[i]);
            }

            return sum;
        }


        throw new RuntimeException("해석 불가 : 올바른 계산식이 아님");
    }

    private static String stripOuterBrackets(String exp) {

        int outerBracketsCount = 0;

        while (exp.charAt(outerBracketsCount) == '(' && exp.charAt(exp.length() - 1 - outerBracketsCount) == ')') {
            outerBracketsCount++;
        }

        if (outerBracketsCount == 0) return exp;

        return exp.substring(outerBracketsCount, exp.length() - outerBracketsCount);
    }
//    private static String stripOuterBracket(String exp) {
//        exp = exp.replace("(", "");
//        exp = exp.replace(")", "");
//        return exp; }
}

