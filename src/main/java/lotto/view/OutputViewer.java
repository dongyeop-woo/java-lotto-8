package lotto.view;

import lotto.domain.Lotto;
import lotto.domain.LottoRank;

import java.util.List;
import java.util.Map;

public class OutputViewer {
    private static final String PURCHASE_MESSAGE = "개를 구매했습니다.";
    private static final String STATISTICS_TITLE = "당첨 통계";
    private static final String DIVIDER = "---";
    private static final String PROFIT_RATE_PREFIX = "총 수익률은 ";
    private static final String PROFIT_RATE_SUFFIX = "%입니다.";

    public static void printPurchaseCount(int count) {
        System.out.println(count + PURCHASE_MESSAGE);
    }

    public static void printLottos(List<Lotto> lottos) {
        for (Lotto lotto : lottos) {
            printLotto(lotto);
        }
    }

    private static void printLotto(Lotto lotto) {
        System.out.println(lotto.getNumbers().toString());
    }

    public static void printStatistics(Map<LottoRank, Integer> statistics) {
        System.out.println();
        System.out.println(STATISTICS_TITLE);
        System.out.println(DIVIDER);
        printRankResults(statistics);
    }

    private static void printRankResults(Map<LottoRank, Integer> statistics) {
        printFifthRank(statistics);
        printFourthRank(statistics);
        printThirdRank(statistics);
        printSecondRank(statistics);
        printFirstRank(statistics);
    }

    private static void printFifthRank(Map<LottoRank, Integer> statistics) {
        LottoRank rank = LottoRank.FIFTH;
        System.out.println(formatRankMessage(rank, statistics.get(rank)));
    }

    private static void printFourthRank(Map<LottoRank, Integer> statistics) {
        LottoRank rank = LottoRank.FOURTH;
        System.out.println(formatRankMessage(rank, statistics.get(rank)));
    }

    private static void printThirdRank(Map<LottoRank, Integer> statistics) {
        LottoRank rank = LottoRank.THIRD;
        System.out.println(formatRankMessage(rank, statistics.get(rank)));
    }

    private static void printSecondRank(Map<LottoRank, Integer> statistics) {
        LottoRank rank = LottoRank.SECOND;
        System.out.println(formatRankMessage(rank, statistics.get(rank)));
    }

    private static void printFirstRank(Map<LottoRank, Integer> statistics) {
        LottoRank rank = LottoRank.FIRST;
        System.out.println(formatRankMessage(rank, statistics.get(rank)));
    }

    private static String formatRankMessage(LottoRank rank, int count) {
        if (rank == LottoRank.SECOND) {
            return rank.getMatchCount() + "개 일치, 보너스 볼 일치 (" + formatPrize(rank.getPrize()) + "원) - " + count + "개";
        }
        return rank.getMatchCount() + "개 일치 (" + formatPrize(rank.getPrize()) + "원) - " + count + "개";
    }

    private static String formatPrize(long prize) {
        return String.format("%,d", prize);
    }

    public static void printProfitRate(double profitRate) {
        System.out.println(PROFIT_RATE_PREFIX + profitRate + PROFIT_RATE_SUFFIX);
    }
}

