package controller;

import domain.LottoResult;
import domain.WinningLotto;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import domain.Lotto;
import service.LottoService;

public class IOController {

    private static final int PRICE = 1000;
    private final LottoService lottoService;

    public IOController(LottoService lottoService) {
        this.lottoService = lottoService;
    }

    public List<Lotto> buyLotto(String input) {
        final int money = parseIntFrom(input);
        validateZero(money);
        validateMoney(money);

        final int lottoCount = money / PRICE;
        final List<Lotto> lottos = lottoService.buyLotto(lottoCount);

        printLottoCount(lottoCount);
        printLottoList(lottos);

        return lottos;
    }

    private int parseIntFrom(String input) {
        validateNull(input);
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 금액은 숫자만 입력해주세요.");
        }
    }

    private void validateNull(String input) {
        if(input == null) {
            throw new IllegalArgumentException("[ERROR] 금액은 null이 아닌 값만 입력해주세요.");
        }
    }

    private void validateZero(int money) {
        if(money == 0) {
            throw new IllegalArgumentException("[ERROR] 금액은 1000원 이상 입력해주세요.");
        }
    }

    private void validateMoney(int money) {
        if (money % PRICE != 0) {
            throw new IllegalArgumentException("[ERROR] 금액은 1000원 단위로 입력해주세요.");
        }
    }

    private void printLottoCount(int lottoCount) {
        System.out.println(lottoCount + "개를 구매했습니다.");
    }

    private void printLottoList(List<Lotto> lottos) {
        for (Lotto lotto : lottos) {
            System.out.println(lotto.toString());
        }
    }

    public Lotto getWinningLotto(String input) {
        final List<Integer> integers = getIntegersFrom(input);

        return Lotto.from(integers);
    }

    private List<Integer> getIntegersFrom(String input) {
        return Arrays.stream(input.split(","))
                .map(this::parseIntFrom)
                .collect(Collectors.toList());
    }

    public void printResult(List<Lotto> purchaseLottos, Lotto winningLottoNumbers, String bonusInput) {
        final int bonusNumber = parseIntFrom(bonusInput);
        final WinningLotto winningLotto = new WinningLotto(winningLottoNumbers, bonusNumber);

        final LottoResult lottoResult = lottoService.getLottoResult(purchaseLottos, winningLotto);
        lottoResult.print(PRICE * purchaseLottos.size());
    }
}
