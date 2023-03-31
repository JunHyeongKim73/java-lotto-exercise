package service;

import java.util.List;

@FunctionalInterface
public interface NumbersGenertor {
    List<Integer> generate(int start, int end, int count);
}
