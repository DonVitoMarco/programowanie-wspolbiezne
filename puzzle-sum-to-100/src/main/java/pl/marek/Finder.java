package pl.marek;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

public class Finder implements Callable<Set<String>> {

    private final static int TARGET_VALUE = 100;
    private String text;

    public Finder(String text) {
        this.text = text;
    }

    @Override
    public Set<String> call() {
        Set<String> equals = new HashSet<>();

        for (int i = 1; i < text.length(); ++i) {
            for (int j = 1; j < text.length(); ++j) {

                if (i == j) {
                    continue;
                }

                if (i < j) {
                    int value1 = Integer.parseInt(text.substring(0, i));
                    int value2 = Integer.parseInt(text.substring(i, j));
                    int value3 = Integer.parseInt(text.substring(j));

                    if (value3 == 0 || value2 % value3 != 0) {
                        continue;
                    }

                    int result = value1 + value2 / value3;
                    if (result == TARGET_VALUE) {
                        equals.add(String.format("%s + %s / %s %n", text.substring(0, i), text.substring(i, j), text.substring(j)));
                    }
                } else {
                    int value1 = Integer.parseInt(text.substring(0, j));
                    int value2 = Integer.parseInt(text.substring(j, i));
                    int value3 = Integer.parseInt(text.substring(i));

                    if (value2 == 0 || value1 % value2 != 0) {
                        continue;
                    }
                    int result = value1 / value2 + value3;
                    if (result == TARGET_VALUE) {
                        equals.add(String.format("%s / %s + %s %n", text.substring(0, j), text.substring(j, i), text.substring(i)));
                    }
                }
            }
        }

        return equals;
    }
}
