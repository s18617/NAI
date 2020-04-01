package languageRecognition;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Text {
    private String language;
    private double[] lettersRatio;

    public Text(String language, String content) {
        this.language = language;
        this.lettersRatio = calcLettersRatio(content);
    }

    public static int[] countLetters(String s) {
        int[] letters = new int[26];

        for (char varChar : s.toLowerCase().toCharArray()) {
            if (varChar >= 'a' && varChar <= 'z') {
                int index = varChar - 'a';
                letters[index]++;
            }
        }

        return letters;
    }

    public static double[] calcLettersRatio(String s) {
        int[] letters = new int[26];
        int sum = 0;

        for (char varChar : s.toLowerCase().toCharArray()) {
            if (varChar >= 'a' && varChar <= 'z') {
                int index = varChar - 'a';
                letters[index]++;
                sum++;
            }
        }

        double[] ratio = new double[26];

        for (int i = 0; i < letters.length; i++) {
            ratio[i] = (letters[i] * 1.0) / sum;
        }

        return ratio;
    }

    public String getLanguage() {
        return language;
    }

    public double[] getLettersRatio() {
        return lettersRatio;
    }

    private double roundDouble(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private double[] roundArray(double[] array, int places) {
        int length = array.length;
        double[] rounded = new double[length];

        for (int i = 0; i < length; i++) {
            rounded[i] = roundDouble(array[i], places);
        }

        return rounded;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');

        for (int i = 0; i < lettersRatio.length; i++) {
            sb.append((char) ('a' + i))
                    .append('=')
                    .append(roundDouble(lettersRatio[i], 5)).append(", ");
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.append(']');

        return language + " " + sb.toString();
    }
}
