package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AwardTest {

    @TestInstance(Lifecycle.PER_CLASS)
    @Nested
    class Award를_생성할_때_ {

        public Stream<Arguments> inValidCounts() {
            return Stream.of(
                    Arguments.of(6, true),
                    Arguments.of(4, true),
                    Arguments.of(2, true)
            );
        }

        public Stream<Arguments> validCounts() {
            return Stream.of(
                    Arguments.of(6, false, Award.FIRST),
                    Arguments.of(5, true, Award.SECOND),
                    Arguments.of(5, false, Award.THIRD),
                    Arguments.of(4, false, Award.FOURTH),
                    Arguments.of(3, false, Award.FIFTH)
            );
        }

        @ParameterizedTest
        @MethodSource("inValidCounts")
        void 입력받은_count에_해당하는_Award가_없으면_빈_값을_담고_있다(int matchCount, boolean bonusMatch) {
            // given
            final Optional<Award> award = Award.from(matchCount, bonusMatch);

            // when & then
            assertThat(award).isEmpty();
        }

        @ParameterizedTest
        @MethodSource("validCounts")
        void counts에_해당하는_Award를_생성한다(int matchCount, boolean bonusMatch, Award expected) {
            // given
            final Optional<Award> award = Award.from(matchCount, bonusMatch);

            // when & then
            assertAll(
                    () -> assertThat(award).isPresent(),
                    () -> assertThat(award.get()).isEqualTo(expected)
            );
        }
    }

    @Test
    void Award는_상금을_기준으로_오름차순으로_정렬되어_있다() {
        // given
        final List<Award> awards = Award.getAwards();

        // when & then
        assertThat(awards).containsExactly(Award.FIFTH, Award.FOURTH, Award.THIRD, Award.SECOND, Award.FIRST);
    }
}