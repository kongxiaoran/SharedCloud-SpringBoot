package com.sharedcloud.Util;

import java.util.ArrayList;
import java.util.Random;

/**
 * @Author: kxr
 * @Date: 2019/9/7
 * @Description
 */
public class ShareUtil {

    //随机产生 n 位验证码    0--9，a--z,A--Z 随机取 n 个数作为验证码
    public static String randomCode(int n) {
        String array = new String();
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            int choice = rand.nextInt(3);
            switch (choice) {
                case 0:
                    int num = rand.nextInt(9);
                    array+=String.valueOf(num);
                    break;
                case 1:
                    int num2 = rand.nextInt(25) + 65;
                    char low = (char) num2;
                    array+=String.valueOf(low);
                    break;
                case 2:
                    int num3 = rand.nextInt(25) + 97;
                    char high = (char) num3;
                    array+=String.valueOf(high);
                    break;
                default:
                    break;
            }
        }
        return array;
    }

    public static void main(String[] args) {
        System.out.println(ShareUtil.randomCode(20));
    }
}
