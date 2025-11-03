package lotto.util;

import camp.nextstep.edu.missionutils.Randoms;
import lotto.domain.Lotto;

import java.util.ArrayList;
import java.util.List;

public class LottoGenerator {
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 45;
    private static final int COUNT = 6;

    public static List<Lotto> generateLottos(int count) {
        List<Lotto> lottos = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            List<Integer> numbers = Randoms.pickUniqueNumbersInRange(MIN_NUMBER, MAX_NUMBER, COUNT);
            Lotto lotto = new Lotto(numbers);
            lottos.add(lotto);
        }
        return lottos;
    }
}

