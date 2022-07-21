package Project.Utils;

import java.util.Random;

public class TokenGenerator {
    private static final String symbols = "abcdefghijklmnopqrstuvwxyz0123456789";

    public static String generate(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; ++i) {
            stringBuilder.append(symbols.charAt(random.nextInt(symbols.length())));
        }
        return stringBuilder.toString();
    }
}
