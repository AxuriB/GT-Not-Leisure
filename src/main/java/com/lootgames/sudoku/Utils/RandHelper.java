package com.lootgames.sudoku.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandHelper {

    public static final Random RAND = new Random();

    /**
     * chance% 概率返回 a，否则返回 b
     */
    public static <T> T chance(int chance, T a, T b) {
        return chance(RAND, chance, a, b);
    }

    public static <T> T chance(Random random, int chance, T a, T b) {
        return chance(random, chance) ? a : b;
    }

    public static boolean chance(int chance) {
        return chance(RAND, chance);
    }

    public static boolean chance(Random random, int chance) {
        if (chance < 0 || chance > 100) {
            throw new IllegalArgumentException("Chance must be between 0 and 100: " + chance);
        }
        return random.nextInt(100) < chance;
    }

    /**
     * 生成 [start, end] 范围内的所有整数并随机打乱
     *
     * @param start 起始值（包含）
     * @param end   结束值（包含）
     * @return 乱序的整数列表
     */
    public static List<Integer> shuffledList(int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException(String.format("Start (%d) must be <= end (%d)", start, end));
        }
        List<Integer> list = new ArrayList<>(end - start + 1);
        for (int i = start; i <= end; i++) {
            list.add(i);
        }
        Collections.shuffle(list, RAND);
        return list;
    }
}
