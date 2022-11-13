package lotto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LottoManager_lottoRankResultTest {

    @BeforeAll
    static void init() throws IllegalArgumentException {
        int numberOfLotto = 1;
        ProgramManager.lotto = LottoManager.generateLotto(numberOfLotto);
    }

    /**
     * getLottoResult - 당첨 내역 계산
     */
    @Test
    void getLottoResult_1등_테스트() {
        List<Integer> userPredictLottoNumbers = ProgramManager.lotto.get(0);
        int userPredictBonusNumber = ProgramManager.lotto.get(0).get(0);

        int[] answerOfLottoRankResult = new int[]{0, 0, 0, 0, 1};
        assertThat(true).isEqualTo(Arrays.equals(answerOfLottoRankResult, LottoManager.getLottoRankResult(userPredictLottoNumbers, userPredictBonusNumber)));
    }

    @Test
    void getLottoResult_2등_테스트() {
        List<Integer> userPredictLottoNumbers = getUserPredictLottoNumbers_modified(2);
        int userPredictBonusNumber_same = userPredictLottoNumbers.get(userPredictLottoNumbers.size() - 1);

        int[] answerOfLottoRankResult = new int[]{0, 0, 0, 1, 0};
        assertThat(true).isEqualTo(Arrays.equals(answerOfLottoRankResult, LottoManager.getLottoRankResult(userPredictLottoNumbers, userPredictBonusNumber_same)));
    }

    @Test
    void getLottoResult_3등_테스트() {
        List<Integer> userPredictLottoNumbers = getUserPredictLottoNumbers_modified(3);
        int userPredictBonusNumber_notSame = userPredictLottoNumbers.get(0);

        int[] answerOfLottoRankResult = new int[]{0, 0, 1, 0, 0};
        assertThat(true).isEqualTo(Arrays.equals(answerOfLottoRankResult, LottoManager.getLottoRankResult(userPredictLottoNumbers, userPredictBonusNumber_notSame)));
    }

    @Test
    void getLottoResult_4등_테스트() {
        List<Integer> userPredictLottoNumbers = getUserPredictLottoNumbers_modified(4);
        int userPredictBonusNumber = userPredictLottoNumbers.get(0);

        int[] answerOfLottoRankResult = new int[]{0, 1, 0, 0, 0};
        assertThat(true).isEqualTo(Arrays.equals(answerOfLottoRankResult, LottoManager.getLottoRankResult(userPredictLottoNumbers, userPredictBonusNumber)));
    }

    @Test
    void getLottoResult_5등_테스트() {
        List<Integer> userPredictLottoNumbers = getUserPredictLottoNumbers_modified(5);
        int userPredictBonusNumber = userPredictLottoNumbers.get(0);

        int[] answerOfLottoRankResult = new int[]{1, 0, 0, 0, 0};
        assertThat(true).isEqualTo(Arrays.equals(answerOfLottoRankResult, LottoManager.getLottoRankResult(userPredictLottoNumbers, userPredictBonusNumber)));
    }

    /**
     * @param rank 테스트 등수
     * @return 테스트에 맞춰서 사용자 입력을 변형한 로또 당첨 번호
     */
    public List<Integer> getUserPredictLottoNumbers_modified(int rank) {
        int sameLottoNumber = LottoManager.LOTTO_LENGTH - rank + 2;
        if (rank == 2) {
            sameLottoNumber = LottoManager.LOTTO_LENGTH - rank + 1;
        }

        List<Integer> lotto = ProgramManager.lotto.get(0);
        List<Integer> userPredictLottoNumbers = new ArrayList<>();

        int number = 1;
        for (int i = 0; i < LottoManager.LOTTO_LENGTH; i++) {
            if (i < sameLottoNumber) {
                userPredictLottoNumbers.add(lotto.get(i));
                continue;
            }
            if (!lotto.contains(number)) {
                userPredictLottoNumbers.add(number++);
                continue;
            }
            number++;
            i--;
        }

        Collections.sort(userPredictLottoNumbers);
        return userPredictLottoNumbers;
    }

}
