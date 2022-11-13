package lotto;

import java.text.DecimalFormat;
import java.util.List;

// 로또 게임 진행자
public class ProgramManager {

    public static final int LOTTO_PRICE = 1000;

    private static final String ASK_PRICE = "구입금액을 입력해 주세요.";
    private static final String NUMBER_OF_LOTTO_MESSAGE = "개를 구매했습니다.";

    private static final String ASK_PREDICT_LOTTO_NUMBER = "당첨 번호를 입력해 주세요.";
    private static final String ASK_PREDICT_LOTTO_BONUS_NUMBER = "보너스 번호를 입력해 주세요.";

    // 당첨 통계
    private static final String LOTTO_STATISTICS_MESSAGE_TITLE = "당첨 통계\n---\n";
    private static final String LOTTO_STATISTICS_MESSAGE_BODY_SAME_NUMBER_PRE = "개 일치";
    private static final String LOTTO_STATISTICS_MESSAGE_BODY_SAME_NUMBER_BONUS_BALL = ", 보너스 볼 일치";
    private static final String LOTTO_STATISTICS_MESSAGE_BODY_REWARD_PRE = " (";
    private static final String LOTTO_STATISTICS_MESSAGE_BODY_REWARD_POST = "원) - ";
    private static final String LOTTO_STATISTICS_MESSAGE_BODY_REWARD_NUMBER = "개\n";
    private static final String LOTTO_STATISTICS_MESSAGE_BODY_REVENUE_RATE_PRE = "총 수익률은 ";
    private static final String LOTTO_STATISTICS_MESSAGE_BODY_REVENUE_RATE_POST = "%입니다.";

    // 에러 메세지
    private static final String ERROR_MESSAGE = "[ERROR]";
    private static final String ERROR_MESSAGE_WRONG_PRICE_VALUE = " 로또는 " + ProgramManager.LOTTO_PRICE + "원 단위로 구매하실 수 있습니다.";
    private static final String ERROR_MESSAGE_WRONG_LOTTO_VALUE = " 로또 번호는 1부터 45 사이의 숫자여야 합니다.";
    private static final String ERROR_MESSAGE_WRONG_BONUS_VALUE = " 보너스 번호는 1부터 45 사이의 숫자여야 합니다.";

    private static int price;
    private static int numberOfLotto;
    private static List<Integer> userPredictLottoNumbers;
    private static int userPredictBonusNumber;

    public static List<List<Integer>> lotto;
    private static int[] lottoRankResult;
    private static float lottoRevenueRate;

    /**
     * TODO: 로또 게임 실행부
     */
    public static void playLottoGame() {
        buyLotto();

        lotto = LottoManager.generateLotto(numberOfLotto);
        printLotto();

        getUserPredictLottoNumbers();

        lottoRankResult = LottoManager.getLottoRankResult(userPredictLottoNumbers, userPredictBonusNumber);
        lottoRevenueRate = LottoManager.getRevenueRate(price, lottoRankResult);

        printLottoStatistics();
    }

    private static void buyLotto() {
        System.out.println(ASK_PRICE);
        try {
            price = UserManager.getInput_price();
        } catch (IllegalArgumentException exception) {
            System.out.println(ERROR_MESSAGE + ERROR_MESSAGE_WRONG_PRICE_VALUE);
        }
        numberOfLotto = price / LOTTO_PRICE;
        System.out.println("\n" + numberOfLotto + NUMBER_OF_LOTTO_MESSAGE);
    }

    private static void printLotto() {
        for (int i = 0; i < numberOfLotto; i++) {
            System.out.println(lotto.get(i));
        }
        System.out.println();
    }

    private static void getUserPredictLottoNumbers() {
        // 당첨 번호 입력받기
        System.out.println(ASK_PREDICT_LOTTO_NUMBER);
        try {
            userPredictLottoNumbers = UserManager.getInput_predictLottoNumber();
        } catch (IllegalArgumentException exception) {
            System.out.println(ERROR_MESSAGE + ERROR_MESSAGE_WRONG_LOTTO_VALUE);
        }

        // 보너스 번호 입력받기
        System.out.println(ASK_PREDICT_LOTTO_BONUS_NUMBER);
        try {
            userPredictBonusNumber = UserManager.getInput_predictBonusLottoNumber();
        } catch (IllegalArgumentException exception) {
            System.out.println(ERROR_MESSAGE + ERROR_MESSAGE_WRONG_BONUS_VALUE);
        }
    }

    private static void printLottoStatistics() {
        String lottoStatisticsMessage
                = LOTTO_STATISTICS_MESSAGE_TITLE + getLottoRankResultMessage() + getLottoRevenueRateMessage();
        System.out.println(lottoStatisticsMessage);
    }

    private static String getLottoRevenueRateMessage() {
        return LOTTO_STATISTICS_MESSAGE_BODY_REVENUE_RATE_PRE + String.format("%.1f", lottoRevenueRate) + LOTTO_STATISTICS_MESSAGE_BODY_REVENUE_RATE_POST;
    }

    private static String getLottoRankResultMessage() {
        DecimalFormat decFormat = new DecimalFormat("###,###");
        StringBuilder lottoRankResultMessage = new StringBuilder();

        for (LottoManager.Rank rank : LottoManager.Rank.values()) {
            lottoRankResultMessage.append(rank.getNumberOfSameLottoNumber());
            lottoRankResultMessage.append(LOTTO_STATISTICS_MESSAGE_BODY_SAME_NUMBER_PRE);

            if (rank.toString().compareTo("second") == 0) {
                lottoRankResultMessage.append(LOTTO_STATISTICS_MESSAGE_BODY_SAME_NUMBER_BONUS_BALL);
            }
            lottoRankResultMessage.append(LOTTO_STATISTICS_MESSAGE_BODY_REWARD_PRE);

            int rewardPrice = LottoManager.Reward.valueOf(rank.toString()).getRewardPrice();
            lottoRankResultMessage.append(decFormat.format(rewardPrice));
            lottoRankResultMessage.append(LOTTO_STATISTICS_MESSAGE_BODY_REWARD_POST);
            lottoRankResultMessage.append(lottoRankResult[rank.getIndexInRankResult()]);
            lottoRankResultMessage.append(LOTTO_STATISTICS_MESSAGE_BODY_REWARD_NUMBER);
        }
        return lottoRankResultMessage.toString();
    }

}
