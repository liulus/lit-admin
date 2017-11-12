package com.github.lit.test.util;

import org.junit.Test;

import java.util.Random;

/**
 * User : liulu
 * Date : 2017/3/11 19:14
 * version $Id: RandomUtils.java, v 0.1 Exp $
 */
public class RandomUtils {

    private static final char[] LOWER_CASE = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private static final char[] UPPER_CASE = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private static final int[] NUMBER = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    @Test
    public void test1 () {
        int length = 5;
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(LOWER_CASE[random.nextInt(26)]);
        }
        System.out.println(sb.toString());
    }

    @Test
    public void test2 () {
        for (int i = 0; i < 10; i++) {
            test1();
        }
    }





}
