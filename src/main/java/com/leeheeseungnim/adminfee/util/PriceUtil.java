package com.leeheeseungnim.adminfee.util;

import org.springframework.stereotype.Component;

@Component
public class PriceUtil {

    public static String formatPrice(int price) {
        return String.format("%,d", price);
    }
}
