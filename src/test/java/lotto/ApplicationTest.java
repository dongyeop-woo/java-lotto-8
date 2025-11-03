package lotto;

import camp.nextstep.edu.missionutils.test.NsTest;
import lotto.domain.Lotto;
import lotto.domain.LottoRank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomUniqueNumbersInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {
    private static final String ERROR_MESSAGE = "[ERROR]";

    @Test
    void 기능_테스트() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    run("8000", "1,2,3,4,5,6", "7");
                    assertThat(output()).contains(
                            "8개를 구매했습니다.",
                            "[8, 21, 23, 41, 42, 43]",
                            "[3, 5, 11, 16, 32, 38]",
                            "[7, 11, 16, 35, 36, 44]",
                            "[1, 8, 11, 31, 41, 42]",
                            "[13, 14, 16, 38, 42, 45]",
                            "[7, 11, 30, 40, 42, 43]",
                            "[2, 13, 22, 32, 38, 45]",
                            "[1, 3, 5, 14, 22, 45]",
                            "3개 일치 (5,000원) - 1개",
                            "4개 일치 (50,000원) - 0개",
                            "5개 일치 (1,500,000원) - 0개",
                            "5개 일치, 보너스 볼 일치 (30,000,000원) - 0개",
                            "6개 일치 (2,000,000,000원) - 0개",
                            "총 수익률은 62.5%입니다."
                    );
                },
                List.of(8, 21, 23, 41, 42, 43),
                List.of(3, 5, 11, 16, 32, 38),
                List.of(7, 11, 16, 35, 36, 44),
                List.of(1, 8, 11, 31, 41, 42),
                List.of(13, 14, 16, 38, 42, 45),
                List.of(7, 11, 30, 40, 42, 43),
                List.of(2, 13, 22, 32, 38, 45),
                List.of(1, 3, 5, 14, 22, 45)
        );
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() -> {
            runException("1000j");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @DisplayName("로또 번호의 개수가 6개가 넘어가면 예외가 발생한다.")
    @Test
    void 로또_번호의_개수가_6개가_넘어가면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void 로또_번호에_중복된_숫자가_있으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호가 1보다 작으면 예외가 발생한다.")
    @Test
    void 로또_번호가_1보다_작으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(0, 1, 2, 3, 4, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호가 45보다 크면 예외가 발생한다.")
    @Test
    void 로또_번호가_45보다_크면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 46)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호의 개수가 6개 미만이면 예외가 발생한다.")
    @Test
    void 로또_번호의_개수가_6개_미만이면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("일치하는 번호 개수를 계산한다.")
    @Test
    void 일치하는_번호_개수를_계산한다() {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        Lotto other = new Lotto(List.of(1, 2, 3, 7, 8, 9));

        assertThat(lotto.countMatchingNumbers(other)).isEqualTo(3);
    }

    @DisplayName("보너스 번호 포함 여부를 확인한다.")
    @Test
    void 보너스_번호_포함_여부를_확인한다() {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        assertThat(lotto.containsBonusNumber(7)).isFalse();
        assertThat(lotto.containsBonusNumber(1)).isTrue();
    }

    @DisplayName("6개 일치하면 1등이다.")
    @Test
    void 일치_6개_1등() {
        LottoRank rank = LottoRank.of(6, false);
        assertThat(rank).isEqualTo(LottoRank.FIRST);
    }

    @DisplayName("5개 일치하고 보너스 번호 일치하면 2등이다.")
    @Test
    void 일치_5개_보너스_2등() {
        LottoRank rank = LottoRank.of(5, true);
        assertThat(rank).isEqualTo(LottoRank.SECOND);
    }

    @DisplayName("5개 일치하고 보너스 번호 미일치하면 3등이다.")
    @Test
    void 일치_5개_보너스_미일치_3등() {
        LottoRank rank = LottoRank.of(5, false);
        assertThat(rank).isEqualTo(LottoRank.THIRD);
    }

    @DisplayName("4개 일치하면 4등이다.")
    @Test
    void 일치_4개_4등() {
        LottoRank rank = LottoRank.of(4, false);
        assertThat(rank).isEqualTo(LottoRank.FOURTH);
    }

    @DisplayName("3개 일치하면 5등이다.")
    @Test
    void 일치_3개_5등() {
        LottoRank rank = LottoRank.of(3, false);
        assertThat(rank).isEqualTo(LottoRank.FIFTH);
    }

    @DisplayName("2개 이하 일치하면 당첨되지 않는다.")
    @Test
    void 일치_2개_이하_미당첨() {
        LottoRank rank = LottoRank.of(2, false);
        assertThat(rank).isEqualTo(LottoRank.NONE);
    }

    @DisplayName("상금을 반환한다.")
    @Test
    void 상금_반환() {
        assertThat(LottoRank.FIRST.getPrize()).isEqualTo(2_000_000_000L);
        assertThat(LottoRank.SECOND.getPrize()).isEqualTo(30_000_000L);
        assertThat(LottoRank.THIRD.getPrize()).isEqualTo(1_500_000L);
        assertThat(LottoRank.FOURTH.getPrize()).isEqualTo(50_000L);
        assertThat(LottoRank.FIFTH.getPrize()).isEqualTo(5_000L);
        assertThat(LottoRank.NONE.getPrize()).isEqualTo(0L);
    }

    @DisplayName("구입 금액이 1000원 단위가 아니면 예외가 발생한다.")
    @Test
    void 구입_금액이_1000원_단위가_아니면_예외가_발생한다() {
        assertThatThrownBy(() -> lotto.util.InputValidator.validatePurchaseAmount("1500"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("구입 금액이 숫자가 아니면 예외가 발생한다.")
    @Test
    void 구입_금액이_숫자가_아니면_예외가_발생한다() {
        assertThatThrownBy(() -> lotto.util.InputValidator.validatePurchaseAmount("abc"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("정상적인 구입 금액은 검증을 통과한다.")
    @Test
    void 정상적인_구입_금액은_검증을_통과한다() {
        assertThat(lotto.util.InputValidator.validatePurchaseAmount("1000")).isEqualTo(1000);
        assertThat(lotto.util.InputValidator.validatePurchaseAmount("5000")).isEqualTo(5000);
    }

    @DisplayName("보너스 번호가 범위를 벗어나면 예외가 발생한다.")
    @Test
    void 보너스_번호가_범위를_벗어나면_예외가_발생한다() {
        Lotto winningLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        assertThatThrownBy(() -> lotto.util.InputValidator.validateBonusNumber("0", winningLotto))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> lotto.util.InputValidator.validateBonusNumber("46", winningLotto))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외가 발생한다.")
    @Test
    void 보너스_번호가_당첨_번호와_중복되면_예외가_발생한다() {
        Lotto winningLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        assertThatThrownBy(() -> lotto.util.InputValidator.validateBonusNumber("1", winningLotto))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("정상적인 보너스 번호는 검증을 통과한다.")
    @Test
    void 정상적인_보너스_번호는_검증을_통과한다() {
        Lotto winningLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        assertThat(lotto.util.InputValidator.validateBonusNumber("7", winningLotto)).isEqualTo(7);
    }

    @DisplayName("로또를 생성한다.")
    @Test
    void 로또_생성() {
        List<Lotto> lottos = lotto.util.LottoGenerator.generateLottos(5);

        assertThat(lottos).hasSize(5);
    }

    @DisplayName("생성된 로또는 각각 6개의 숫자를 가진다.")
    @Test
    void 로또_숫자_개수() {
        List<Lotto> lottos = lotto.util.LottoGenerator.generateLottos(1);

        assertThat(lottos.get(0).getNumbers()).hasSize(6);
    }

    @DisplayName("로또 개수를 계산한다.")
    @Test
    void 로또_개수_계산() {
        assertThat(lotto.util.LottoStatistics.calculateLottoCount(1000)).isEqualTo(1);
        assertThat(lotto.util.LottoStatistics.calculateLottoCount(5000)).isEqualTo(5);
    }

    @DisplayName("당첨 통계를 계산한다.")
    @Test
    void 당첨_통계_계산() {
        List<Lotto> lottos = List.of(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                new Lotto(List.of(1, 2, 3, 4, 5, 7)),
                new Lotto(List.of(1, 2, 3, 4, 10, 11))
        );
        Lotto winningLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        int bonusNumber = 7;

        Map<LottoRank, Integer> statistics = lotto.util.LottoStatistics.calculateStatistics(
                lottos, winningLotto, bonusNumber);

        assertThat(statistics.get(LottoRank.FIRST)).isEqualTo(1);
        assertThat(statistics.get(LottoRank.SECOND)).isEqualTo(1);
        assertThat(statistics.get(LottoRank.FOURTH)).isEqualTo(1);
    }

    @DisplayName("수익률을 계산한다.")
    @Test
    void 수익률_계산() {
        Map<LottoRank, Integer> statistics = new HashMap<>();
        statistics.put(LottoRank.FIRST, 0);
        statistics.put(LottoRank.SECOND, 0);
        statistics.put(LottoRank.THIRD, 0);
        statistics.put(LottoRank.FOURTH, 0);
        statistics.put(LottoRank.FIFTH, 1);
        statistics.put(LottoRank.NONE, 0);

        double profitRate = lotto.util.LottoStatistics.calculateProfitRate(statistics, 10000);

        assertThat(profitRate).isEqualTo(50.0);
    }

    @DisplayName("수익률을 소수점 둘째 자리에서 반올림한다.")
    @Test
    void 수익률_반올림() {
        Map<LottoRank, Integer> statistics = new HashMap<>();
        statistics.put(LottoRank.FIRST, 0);
        statistics.put(LottoRank.SECOND, 0);
        statistics.put(LottoRank.THIRD, 1);
        statistics.put(LottoRank.FOURTH, 0);
        statistics.put(LottoRank.FIFTH, 0);
        statistics.put(LottoRank.NONE, 0);

        double profitRate = lotto.util.LottoStatistics.calculateProfitRate(statistics, 1000);

        assertThat(profitRate).isEqualTo(150000.0);
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
