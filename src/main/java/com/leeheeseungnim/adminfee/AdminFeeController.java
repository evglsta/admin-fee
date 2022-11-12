package com.leeheeseungnim.adminfee;

import com.leeheeseungnim.adminfee.entity.AdminFee;
import com.leeheeseungnim.adminfee.entity.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin-fee")
public class AdminFeeController {

    @Autowired
    private AdminFeeService service;

    AdminFee adminFee;

    @GetMapping
    public String index(Model model) {
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
    public String addPrice(Model model) {
        if (adminFee != null) {
            adminFee.getPrices().add(new Price());
        }

        model.addAttribute("adminFee", adminFee);
        return "index";
    }

    @PostMapping(params = {"reset"})
    public String reset(Model model) {
        if (adminFee != null) {
            List<Price> prices = new ArrayList<>();
            prices.add(new Price());
            adminFee.setPrices(prices);
        }

        model.addAttribute("adminFee", adminFee);
        return "index";
    }

    @PostMapping
    public String count(@ModelAttribute AdminFee adminFee, Model model) {
        this.adminFee = service.getPriceWithAdminFee(adminFee);
        model.addAttribute("adminFee", this.adminFee);
        return "redirect:/admin-fee";
    }

    @PostMapping(params = {"removePrice"})
    public String removePrice(HttpServletRequest request, Model model) {
        if (adminFee != null) {
            adminFee.getPrices().remove(Integer.parseInt(request.getParameter("removePrice")));
        }

        model.addAttribute("adminFee", adminFee);
        return "index";
    }

    @PostMapping(path = "/marketplace")
    public String getMarketplace(@ModelAttribute AdminFee adminFee, Model model) {
        this.adminFee = adminFee;
        model.addAttribute("adminFee", this.adminFee);
        return "redirect:/admin-fee";
    }
}
