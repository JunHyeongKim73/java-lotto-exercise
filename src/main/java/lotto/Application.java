package lotto;

import camp.nextstep.edu.missionutils.Console;
import controller.IOController;
import domain.Lotto;
import java.util.List;
import service.LottoService;
import service.LottoServiceImpl;
import service.NumbersGenertor;
import service.RandomNumbersGenerator;

public class Application {
    public static void main(String[] args) {
        final NumbersGenertor numbersGenertor = new RandomNumbersGenerator();
        final LottoService lottoService = new LottoServiceImpl(numbersGenertor);
        final IOController ioController = new IOController(lottoService);

        System.out.println("구입금액을 입력해 주세요.");
        final String purchaseInput = Console.readLine();
        final List<Lotto> purchaseLottos = ioController.buyLotto(purchaseInput);

        System.out.println("당첨번호를 입력해 주세요.");
        final String winningInput = Console.readLine();
        final Lotto winningLotto = ioController.getWinningLotto(winningInput);

        System.out.println("보너스 번호를 입력해 주세요.");
        final String bonusInput = Console.readLine();

        ioController.printResult(purchaseLottos, winningLotto, bonusInput);
    }
}
