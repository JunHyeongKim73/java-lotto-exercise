package domain;

import java.util.Optional;

public class WinningLotto {

    private final Lotto lotto;
    private final int bonusNumber;

    public WinningLotto(Lotto lotto, int bonusNumber) {
        if (lotto.contains(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }

        this.lotto = lotto;
        this.bonusNumber = bonusNumber;
    }

    public Optional<Award> getAward(Lotto lotto) {
        final int matchCount = this.lotto.getMatchCount(lotto);
        final boolean matchBonus = lotto.contains(bonusNumber);

        return Award.from(matchCount, matchBonus);
    }
}
