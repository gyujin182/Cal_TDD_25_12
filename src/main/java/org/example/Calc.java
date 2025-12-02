package org.example;

public class Calc {

    public static int run(String exp) {
        exp = exp.replace("- ", "+ -");
        String[] bits = exp.split(" \\+ | \\* ");
        boolean needTomul = exp.contains("* ");
        int multiplier = 1;
        if (needTomul) {
            for (int i = 0; i < bits.length; i++) {
                multiplier *= Integer.parseInt(bits[i]);
            }

            return multiplier;
        }
        int sum = 0;

        for (int i = 0; i < bits.length; i++) {
            sum += Integer.parseInt(bits[i]);
        }

        return sum;


//        throw new RuntimeException("해석 불가 : 올바른 계산식이 아님");
    }


}