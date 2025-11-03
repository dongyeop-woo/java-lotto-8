package lotto.util;

import lotto.domain.Lotto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InputParser {
    private static final String SEPARATOR = ",";

    public static Lotto parseWinningNumbers(String input) {
        List<Integer> numbers = parseNumbers(input);
        return new Lotto(numbers);
    }

    private static List<Integer> parseNumbers(String input) {
        String[] parts = input.split(SEPARATOR);
        return Arrays.stream(parts)
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}

