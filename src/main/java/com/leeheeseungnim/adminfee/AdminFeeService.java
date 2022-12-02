package com.leeheeseungnim.adminfee;

import com.leeheeseungnim.adminfee.entity.AdminFee;
import com.leeheeseungnim.adminfee.entity.Price;
import com.leeheeseungnim.adminfee.enumerator.MarketplaceEnum;
import com.leeheeseungnim.adminfee.util.PriceUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminFeeService {

    private final int maxAdminFeeTokopedia = 10000;
    private final int fixedAdminTiktokShop = 2000;
    private final float fixedAdminPercentageTiktokShop = (float) 1/100;

    public AdminFee getPriceWithAdminFee(AdminFee adminFee) {
        if (adminFee.getMarketplace().equals(MarketplaceEnum.SHOPEE.getValue())) {
            adminFee = this.getPriceShopee(adminFee);
        } else if (adminFee.getMarketplace().equals(MarketplaceEnum.TOKOPEDIA.getValue())) {
            adminFee = this.getPriceTokopedia(adminFee);
        } else {
            adminFee = this.getPriceTiktokShop(adminFee);
        }

        return adminFee;
    }

    private AdminFee getPriceShopee(AdminFee adminFee) {
        float adminFeeMinus100 = 100 - adminFee.getAdminFeeShopee();

        List<Price> prices = adminFee.getPrices();
        for (int i = 0; i < prices.size(); i++) {
            Price price = prices.get(i);
            float priceAfterAdminFee = price.getPrice() / adminFeeMinus100 * 100;

            price.setPriceAfterAdmin(PriceUtil.formatPrice(Math.round(priceAfterAdminFee)));
            prices.set(i, price);
        }

        return adminFee;
    }

    public AdminFee getPriceTokopedia(AdminFee adminFee) {
        List<Price> prices = adminFee.getPrices();
        for (int i = 0; i < prices.size(); i++) {
            Price price = prices.get(i);
            float merchantDec = adminFee.getAdminFeeMerchant() / 100;
            float ongkirDec = adminFee.getAdminFeeFreeOng() / 100;

            float adminFeeMerchant = price.getPrice() * merchantDec;
            float adminFeeFreeOng = price.getPrice() * ongkirDec;

            int up = price.getPrice();
            float divider = 1;
            if (Math.round(adminFeeFreeOng) > maxAdminFeeTokopedia) {
                up += maxAdminFeeTokopedia;
            } else {
                divider -= ongkirDec;
            }

            if (Math.round(adminFeeMerchant) > maxAdminFeeTokopedia) {
                up += maxAdminFeeTokopedia;
            } else {
                divider -= merchantDec;
            }

            float priceAfterAdminFee = up / divider;
            price.setPriceAfterAdmin(PriceUtil.formatPrice(Math.round(priceAfterAdminFee)));
            prices.set(i, price);
        }

        return adminFee;
    }

    private AdminFee getPriceTiktokShop(AdminFee adminFee) {
        List<Price> prices = adminFee.getPrices();
        for (int i = 0; i < prices.size(); i++) {
            Price price = prices.get(i);

            float priceAfterAdminFee = (price.getPrice() + fixedAdminTiktokShop) / (1 - fixedAdminPercentageTiktokShop);
            price.setPriceAfterAdmin(PriceUtil.formatPrice(Math.round(priceAfterAdminFee)));
            prices.set(i, price);
        }

        return adminFee;
    }
}
