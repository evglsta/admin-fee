package com.leeheeseungnim.adminfee.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdminFee {

    private String marketplace;
    private float adminFeeShopee;
    private float adminFeeMerchant;
    private float adminFeeFreeOng;
    private List<Price> prices;
}
