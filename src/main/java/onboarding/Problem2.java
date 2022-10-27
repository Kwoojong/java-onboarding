package onboarding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Problem2 {
    public static String solution(String cryptogram) {
        return decoder(cryptogram);
    }

    private static String decoder(String cryptogram) {
        if (cryptogram.length() < 1 || cryptogram.length() > 1000 || !cryptogram.matches("^[a-z]*$"))
            return cryptogram;

        String answer =  cryptogram.replaceAll("([a-z])\\1","");

        if (answer.matches("^[a-z]*([a-z])\\1[a-z]*$"))
            return decoder(answer);

        return answer;
    }
}
