package domain;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class LottoResult {
    private final List<Award> values;

    public LottoResult(List<Award> values) {
        this.values = new ArrayList<>(values);
    }

    public void print(int purchaseMoney) {
        System.out.println("당첨 통계");
        System.out.println("---");

        int totalPrize = 0;
        for (Award award : Award.getAwards()) {
            final int prize = award.getPrize();
            final int winningCount = count(award);
            totalPrize += prize * winningCount;

            System.out.print(award.getMatchCount() + "개 일치");
            if (award.isBonusMatch()) {
                System.out.print(", 보너스 볼 일치");
            }
            System.out.println(" (" + commaPrize(prize) + "원) - " + winningCount + "개");
        }

        System.out.println("총 수익률은 " + calculateProfit(purchaseMoney, totalPrize) + "%입니다.");
    }

    private double calculateProfit(int purchaseMoney, int totalPrize) {
        return (double) totalPrize / purchaseMoney * 100;
    }

    private String commaPrize(int prize) {
        final DecimalFormat decimalFormat = new DecimalFormat("#, ###");

        return decimalFormat.format(prize).trim();
    }

    private int count(Award award) {
        return (int) values.stream()
                .filter(value -> value == award)
                .count();
    }
}
