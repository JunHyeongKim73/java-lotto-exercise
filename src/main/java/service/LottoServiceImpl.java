package service;

import domain.Award;
import domain.LottoResult;
import domain.WinningLotto;
import java.util.ArrayList;
import java.util.List;
import domain.Lotto;
import java.util.Optional;

public class LottoServiceImpl implements LottoService {

    private final NumbersGenertor numbersGenertor;

    public LottoServiceImpl(NumbersGenertor numbersGenertor) {
        this.numbersGenertor = numbersGenertor;
    }

    @Override
    public List<Lotto> buyLotto(int lottoCount) {
        final List<Lotto> lottos = new ArrayList<>();
        for(int i = 0; i < lottoCount; i++) {
            lottos.add(Lotto.from(numbersGenertor));
        }

        return lottos;
    }

    @Override
    public LottoResult getLottoResult(List<Lotto> lottos, WinningLotto winningLotto) {
        final List<Award> values = new ArrayList<>();
        for (Lotto lotto : lottos) {
            final Optional<Award> award = winningLotto.getAward(lotto);
            award.ifPresent(values::add);
        }

        return new LottoResult(values);
    }
}
