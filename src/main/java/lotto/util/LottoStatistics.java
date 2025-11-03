package lotto.util;

import lotto.domain.Lotto;
import lotto.domain.LottoRank;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LottoStatistics {
    private static final int LOTTO_PRICE = 1000;

    public static Map<LottoRank, Integer> calculateStatistics(
            List<Lotto> lottos, Lotto winningLotto, int bonusNumber) {
        Map<LottoRank, Integer> statistics = initializeStatistics();
        for (Lotto lotto : lottos) {
            LottoRank rank = determineRank(lotto, winningLotto, bonusNumber);
            statistics.put(rank, statistics.get(rank) + 1);
        }
        return statistics;
    }

    private static Map<LottoRank, Integer> initializeStatistics() {
        Map<LottoRank, Integer> statistics = new EnumMap<>(LottoRank.class);
        for (LottoRank rank : LottoRank.values()) {
            statistics.put(rank, 0);
        }
        return statistics;
    }

    private static LottoRank determineRank(Lotto lotto, Lotto winningLotto, int bonusNumber) {
        int matchCount = lotto.countMatchingNumbers(winningLotto);
        boolean hasBonus = lotto.containsBonusNumber(bonusNumber);
        return LottoRank.of(matchCount, hasBonus);
    }

    public static double calculateProfitRate(
            Map<LottoRank, Integer> statistics, int purchaseAmount) {
        long totalPrize = calculateTotalPrize(statistics);
        return calculateRate(totalPrize, purchaseAmount);
    }

    private static long calculateTotalPrize(Map<LottoRank, Integer> statistics) {
        long totalPrize = 0;
        for (LottoRank rank : statistics.keySet()) {
            totalPrize += (long) rank.getPrize() * statistics.get(rank);
        }
        return totalPrize;
    }

    private static double calculateRate(long totalPrize, int purchaseAmount) {
        double rawRate = ((double) totalPrize / purchaseAmount) * 100;
        return Math.round(rawRate * 10) / 10.0;
    }

    public static int calculateLottoCount(int purchaseAmount) {
        return purchaseAmount / LOTTO_PRICE;
    }
}

