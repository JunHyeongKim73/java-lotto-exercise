package domain;

import java.util.List;
import java.util.stream.Collectors;
import service.NumbersGenertor;

public class Lotto {
    private static final int START_INCLUSIVE = 1;
    private static final int END_INCLUSIVE = 45;
    private static final int COUNT = 6;

    private final List<Integer> numbers;

    public static Lotto from(NumbersGenertor numbersGenertor) {
        return new Lotto(numbersGenertor.generate(START_INCLUSIVE, END_INCLUSIVE, COUNT));
    }

    public static Lotto from(List<Integer> numbers) {
        return new Lotto(numbers);
    }

    private Lotto(List<Integer> numbers) {
        validateLength(numbers);
        validateDuplicate(numbers);

        this.numbers = sortedNumbers(numbers);
    }

    private void validateLength(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException();
        }
    }

    private void validateDuplicate(List<Integer> numbers) {
        if (numbers.stream().distinct().count() != numbers.size()) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 중복될 수 없습니다.");
        }
    }

    private List<Integer> sortedNumbers(List<Integer> numbers) {
        return numbers.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public int getMatchCount(Lotto lotto) {
        return (int) this.numbers.stream()
                .filter(lotto::contains)
                .count();
    }

    public boolean contains(int number) {
        return this.numbers.contains(number);
    }

    @Override
    public String toString() {
        final String numbersString = numbers.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));

        return "[" + numbersString + "]";
    }
}
