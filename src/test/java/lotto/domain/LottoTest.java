package lotto.domain;

import domain.Lotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import service.NumbersGenertor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoTest {

    @Nested
    @DisplayName("Lotto.From()으로 Lotto 객체를 생성할 때")
    class createLotto {

        @Test
        void 로또_번호의_개수가_6개가_넘어가면_예외가_발생한다() {
            assertThatThrownBy(() -> Lotto.from(List.of(1, 2, 3, 4, 5, 6, 7)))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 중복된_숫자가_있으면_예외가_발생한다() {
            assertThatThrownBy(() -> Lotto.from(List.of(1, 2, 3, 4, 5, 5)))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 로또_번호는_정렬되어야_한다() {
            // given
            final List<Integer> sortedIntegers = List.of(1, 2, 3, 4, 5, 6);
            final List<Integer> notSortedIntegers = List.of(6, 5, 4, 3, 2, 1);
            final NumbersGenertor numbersGenertor = (start, end, count) -> notSortedIntegers;

            // when
            final Lotto lotto = Lotto.from(numbersGenertor);

            // then
            assertThat(lotto).usingRecursiveComparison().isEqualTo(Lotto.from(sortedIntegers));
        }
    }

    @Nested
    @DisplayName("Lotto.contains() 메서드를 호출할 때")
    class contains {

        @Test
        void 입력_받은_숫자가_로또에_포함된다면_true를_반환한다() {
            // given
            final Lotto lotto = Lotto.from(List.of(1, 2, 3, 4, 5, 6));

            // when
            final boolean contains = lotto.contains(1);

            // then
            assertThat(contains).isTrue();
        }

        @Test
        void 입력_받은_숫자가_로또에_포함되지_않는다면_false를_반환한다() {
            // given
            final Lotto lotto = Lotto.from(List.of(1, 2, 3, 4, 5, 6));

            // when
            final boolean contains = lotto.contains(7);

            // then
            assertThat(contains).isFalse();
        }
    }
}
