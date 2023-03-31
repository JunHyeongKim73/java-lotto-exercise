package service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import domain.Lotto;
import org.junit.jupiter.api.Test;

class LottoServiceImplTest {

    LottoService lottoService;

    @Test
    void 로또를_구매한다() {
        // given
        final NumbersGenertor numbersGenertor = (start, end, count) -> List.of(6, 5, 4, 3, 2, 1);
        lottoService = new LottoServiceImpl(numbersGenertor);
        final int lottoCount = 2;

        // when
        final List<Lotto> lottos = lottoService.buyLotto(lottoCount);

        // then
        assertThat(lottos).hasSize(lottoCount);
    }
}