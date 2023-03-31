package domain;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum Award {
    FIRST(6, false, 2000000000),
    SECOND(5, true, 30000000),
    THIRD(5, false, 1500000),
    FOURTH(4, false, 50000),
    FIFTH(3, false, 5000);

    private final int matchCount;
    private final boolean bonusMatch;
    private final int prize;

    Award(int matchCount, boolean bonusMatch, int prize) {
        this.matchCount = matchCount;
        this.bonusMatch = bonusMatch;
        this.prize = prize;
    }

    public static Optional<Award> from(int matchCount, boolean bonusMatch) {
        return Arrays.stream(Award.values())
                .filter(award -> award.matchCount == matchCount && award.bonusMatch == bonusMatch)
                .findAny();
    }

    public static List<Award> getAwards() {
        return Arrays.stream(Award.values())
                .sorted(Comparator.comparing(Award::getPrize))
                .collect(Collectors.toList());
    }

    public int getMatchCount() {
        return matchCount;
    }

    public boolean isBonusMatch() {
        return bonusMatch;
    }

    public int getPrize() {
        return prize;
    }
}
