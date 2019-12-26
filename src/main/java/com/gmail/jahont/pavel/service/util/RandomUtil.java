package com.gmail.jahont.pavel.service.util;

import java.util.Random;

public class RandomUtil {

    private static final Random random = new Random();

    public static int getElement() {
        return random.nextInt();
    }

    public static int getElement(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public static int[] getArrayInRange(int randomNumberRange, int numberRange) {
        return random.ints(-randomNumberRange, randomNumberRange).limit(numberRange).toArray();
    }
}
