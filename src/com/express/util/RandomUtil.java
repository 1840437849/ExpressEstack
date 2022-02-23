package com.express.util;

import java.util.Random;

//专门用来生成随机数
public class RandomUtil {
    private static Random r = new Random();

    public static int getCode() {
        return r.nextInt(900000) + 100000;
    }
}
