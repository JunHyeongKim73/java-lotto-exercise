package service;

import domain.LottoResult;
import domain.WinningLotto;
import java.util.List;
import domain.Lotto;

public interface LottoService {

    List<Lotto> buyLotto(int lottoCount);
    LottoResult getLottoResult(List<Lotto> lottos, WinningLotto winningLotto);
}
