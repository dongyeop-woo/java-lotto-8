package lotto.controller;

import lotto.domain.Lotto;
import lotto.domain.LottoRank;
import lotto.util.InputParser;
import lotto.util.InputValidator;
import lotto.util.LottoGenerator;
import lotto.util.LottoStatistics;
import lotto.view.InputViewer;
import lotto.view.OutputViewer;

import java.util.List;
import java.util.Map;

public class LottoController {
    public void run() {
        int purchaseAmount = getPurchaseAmount();
        List<Lotto> lottos = LottoGenerator.generateLottos(
                LottoStatistics.calculateLottoCount(purchaseAmount));
        printLottos(lottos);
        Lotto winningLotto = getWinningLotto();
        int bonusNumber = getBonusNumber(winningLotto);
        Map<LottoRank, Integer> statistics = LottoStatistics.calculateStatistics(
                lottos, winningLotto, bonusNumber);
        printStatistics(statistics, purchaseAmount);
    }

    private int getPurchaseAmount() {
        while (true) {
            try {
                String input = InputViewer.readPurchaseAmount();
                return InputValidator.validatePurchaseAmount(input);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Lotto getWinningLotto() {
        while (true) {
            try {
                String input = InputViewer.readWinningNumbers();
                return InputParser.parseWinningNumbers(input);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int getBonusNumber(Lotto winningLotto) {
        while (true) {
            try {
                String input = InputViewer.readBonusNumber();
                return InputValidator.validateBonusNumber(input, winningLotto);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printLottos(List<Lotto> lottos) {
        OutputViewer.printPurchaseCount(lottos.size());
        OutputViewer.printLottos(lottos);
    }

    private void printStatistics(Map<LottoRank, Integer> statistics, int purchaseAmount) {
        OutputViewer.printStatistics(statistics);
        double profitRate = LottoStatistics.calculateProfitRate(statistics, purchaseAmount);
        OutputViewer.printProfitRate(profitRate);
    }
}

