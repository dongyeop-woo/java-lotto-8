package lotto.util;

import lotto.domain.Lotto;

public class InputValidator {
    private static final int LOTTO_PRICE = 1000;
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 45;

    public static int validatePurchaseAmount(String input) {
        validateNumeric(input);
        int amount = Integer.parseInt(input);
        validateAmountDivisibleByPrice(amount);
        return amount;
    }

    private static void validateNumeric(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자만 입력 가능합니다.");
        }
    }

    private static void validateAmountDivisibleByPrice(int amount) {
        if (amount % LOTTO_PRICE != 0) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 1,000원 단위로 입력해야 합니다.");
        }
    }

    public static int validateBonusNumber(String input, Lotto winningLotto) {
        validateNumeric(input);
        int bonusNumber = Integer.parseInt(input);
        validateNumberRange(bonusNumber);
        validateNotDuplicate(bonusNumber, winningLotto);
        return bonusNumber;
    }

    private static void validateNumberRange(int number) {
        if (number < MIN_NUMBER || number > MAX_NUMBER) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
    }

    private static void validateNotDuplicate(int bonusNumber, Lotto winningLotto) {
        if (winningLotto.containsBonusNumber(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }
}

