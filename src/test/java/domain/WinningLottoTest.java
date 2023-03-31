package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class WinningLottoTest {

    @Nested
    @DisplayName("WinningLotto 객체를 생성할 때_")
    class createWinningLotto {
        final Lotto lotto = Lotto.from(List.of(1, 2, 3, 4, 5, 6));

        @Test
        void 보너스_번호와_당첨_번호가_중복된다면_예외를_던진다() {
            // given
            final int bonusNumber = 6;

            // when & then
            assertThatThrownBy(() -> new WinningLotto(lotto, bonusNumber))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 보너스_번호와_당첨_번호가_중복되지_않는다면_객체를_생성한다() {
            // given
            final int bonusNumber = 7;

            // when
            final WinningLotto winningLotto = new WinningLotto(lotto, bonusNumber);

            // then
            assertThat(winningLotto).isNotNull();
        }
    }

    @Nested
    class getAward_메서드_실행_시_ {
        final Lotto winningLottoNumbers = Lotto.from(List.of(1, 2, 3, 4, 5, 6));
        final int bonusNumber = 7;
        final WinningLotto winningLotto = new WinningLotto(winningLottoNumbers, bonusNumber);

        @Test
        void 로또_번호가_당첨되지_않았다면_빈_값을_반환한다() {
            // given
            final Lotto invalidLotto = Lotto.from(List.of(7, 8, 9, 10, 11, 12));

            // when
            final Optional<Award> award = winningLotto.getAward(invalidLotto);

            // then
            assertThat(award).isEmpty();
        }

        @Test
        void 로또_번호가_당첨되었다면_Award_객체를_반환한다() {
            // given
            final Lotto invalidLotto = Lotto.from(List.of(1, 2, 3, 4, 5, 6));

            // when
            final Optional<Award> award = winningLotto.getAward(invalidLotto);

            // then
            assertAll(
                    () -> assertThat(award).isNotEmpty(),
                    () -> assertThat(award.get()).isEqualTo(Award.FIRST)
            );
        }
    }
}