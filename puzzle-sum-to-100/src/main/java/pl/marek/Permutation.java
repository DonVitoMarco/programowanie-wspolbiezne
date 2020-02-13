package pl.marek;

import java.util.HashSet;
import java.util.Set;

public class Permutation {

    public Set<String> permutation(String str) {
        Set<String> result = new HashSet<>();
        if (str == null) {
            return null;
        } else if (str.length() == 0) {
            result.add("");
            return result;
        }

        char firstChar = str.charAt(0);
        String rem = str.substring(1);
        Set<String> words = permutation(rem);
        for (String newString : words) {
            for (int i = 0; i <= newString.length(); i++) {
                result.add(charAdd(newString, firstChar, i));
            }
        }

        return result;
    }

    public String charAdd(String str, char c, int j) {
        String first = str.substring(0, j);
        String last = str.substring(j);

        return first + c + last;
    }
}