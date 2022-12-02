package com.leeheeseungnim.adminfee.enumerator;

public enum MarketplaceEnum {

    SHOPEE("Shopee"),
    TOKOPEDIA("Tokpedia"),
    TIKTOK_SHOP("Tiktok Shop");

    private String text;

    MarketplaceEnum(String text) {
        this.text = text;
    }

    public String getValue() {
        return this.text;
    }
}
