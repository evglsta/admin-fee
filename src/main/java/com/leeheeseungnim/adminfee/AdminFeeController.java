package com.leeheeseungnim.adminfee;

import com.leeheeseungnim.adminfee.entity.AdminFee;
import com.leeheeseungnim.adminfee.entity.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin-fee")
public class AdminFeeController {

    @Autowired
    private AdminFeeService service;

    @GetMapping
    public String index(Model model, AdminFee adminFee) {
        if (adminFee == null) {
            adminFee = new AdminFee();

            List<Price> prices = new ArrayList<>();
            prices.add(new Price());
            adminFee.setPrices(prices);
        } else {
            if (adminFee.getPrices() == null) {
                List<Price> prices = new ArrayList<>();
                prices.add(new Price());
                adminFee.setPrices(prices);
            }
        }

        model.addAttribute("adminFee", adminFee);
        return "index";
    }

    @PostMapping(params = {"addPrice"})
    public String addPrice(AdminFee adminFee) {
        if (adminFee != null) {
            adminFee.getPrices().add(new Price());
        }

        return "index";
    }

    @PostMapping(params = {"reset"})
    public String reset(AdminFee adminFee) {
        if (adminFee != null) {
            adminFee.setAdminFeeShopee(0);
            adminFee.setAdminFeeMerchant(0);
            adminFee.setAdminFeeFreeOng(0);

            List<Price> prices = new ArrayList<>();
            prices.add(new Price());
            adminFee.setPrices(prices);
        }

        return "index";
    }

    @PostMapping
    public String count(@ModelAttribute AdminFee adminFee, BindingResult result, RedirectAttributes redirectAttributes) {
        AdminFee newAdminFee = service.getPriceWithAdminFee(adminFee);
        redirectAttributes.addFlashAttribute("adminFee", newAdminFee);

        return "redirect:/admin-fee";
    }

    @PostMapping(params = {"removePrice"})
    public String removePrice(AdminFee adminFee, HttpServletRequest request) {
        if (adminFee != null) {
            adminFee.getPrices().remove(Integer.parseInt(request.getParameter("removePrice")));
        }

        return "index";
    }

    @PostMapping(path = "/marketplace")
    public String getMarketplace(@ModelAttribute AdminFee adminFee, BindingResult result, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("adminFee", adminFee);
        return "redirect:/admin-fee";
    }
}
