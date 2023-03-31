package controller;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import service.LottoService;
import service.LottoServiceImpl;
import service.NumbersGenertor;
import service.RandomNumbersGenerator;

class IOControllerTest {

    final NumbersGenertor numbersGenertor = new RandomNumbersGenerator();
    final LottoService lottoService = new LottoServiceImpl(numbersGenertor);
    final IOController ioController = new IOController(lottoService);

    @Nested
    class 로또를_구입할_때_ {

        @ParameterizedTest
        @ValueSource(strings = {"a", "$", "한"})
        @NullAndEmptySource
        void 입력에_숫자가_아닌_문자가_포함되어_있으면_예외를_던진다(String money) {
            assertThatThrownBy(() -> ioController.buyLotto(money))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @ValueSource(strings = {"0", "1500", "1550", "1555"})
        void _1000원_단위가_아니면_예외를_던진다(String money) {
            assertThatThrownBy(() -> ioController.buyLotto(money))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @ValueSource(strings = {"1000", "2000", "3000"})
        void _1000원_단위이면_예외를_던지지_않는다(String money) {
            assertThatCode(() -> ioController.buyLotto(money))
                    .doesNotThrowAnyException();
        }
    }

    @Nested
    class 당첨번호를_입력했을_때_ {

        @ParameterizedTest
        @ValueSource(strings = {"1|2|3|4|5|6", "1a2a3a4a5a6a"})
        void 숫자가_콤마로_구분되지_않으면_예외를_던진다(String input) {
            assertThatThrownBy(() -> ioController.getWinningLotto(input))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @ValueSource(strings = {"a,b,c,d,e,f", "1,2,3,4,5,@"})
        void 문자가_콤마로_구분되지_있으면_예외를_던진다(String input) {
            assertThatThrownBy(() -> ioController.getWinningLotto(input))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @ValueSource(strings = {"1,2,3,4,5,6", "2,3,4,5,6,7", "1,2,3 , 4, 5, 6", "6,5,4,3,2,1"})
        void 띄어쓰기가_있어도_숫자가_콤마로_구분되지_있으면_예외를_던지지_않는다(String input) {
            assertThatCode(() -> ioController.getWinningLotto(input))
                    .doesNotThrowAnyException();
        }
    }
}