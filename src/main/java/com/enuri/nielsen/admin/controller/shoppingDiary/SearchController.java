package com.enuri.nielsen.admin.controller.shoppingDiary;

import com.enuri.nielsen.admin.domain.shoppingDiary.BuyHistory;
import com.enuri.nielsen.admin.domain.shoppingDiary.compositePrimaryKeyClass.BuyHistoryId;
import com.enuri.nielsen.admin.domain.shoppingDiary.formDto.SearchInputForm;
import com.enuri.nielsen.admin.domain.shoppingDiary.viewDto.SearchResult;
import com.enuri.nielsen.admin.repository.shoppingDiary.BuyHistoryRepository;
import com.enuri.nielsen.admin.service.shoppingDiary.BuyHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;

@Controller
public class SearchController {

    @Autowired
    BuyHistoryRepository buyHistoryRepository;
    @Autowired
    BuyHistoryService buyHistoryService;

    @GetMapping("/")
    public String indexView() {
        return "index";
    }

    @GetMapping("/admin/search")
    public String adminMainView(@Valid SearchInputForm searchInputForm, Model model, Error error) {
        System.out.println(searchInputForm.toString());
        SearchResult searchResult = buyHistoryService.search(searchInputForm);
        model.addAttribute("searchInputForm", searchInputForm);
        return "admin/search";
    }

    @GetMapping("/admin/test")
    public String test() {
        BuyHistoryId buyHistoryId = new BuyHistoryId(158, 9);
        BuyHistory buyHistory = buyHistoryRepository.findById(buyHistoryId).get();
        System.out.println("buyDate : " + buyHistory.getBuyDate() + "\n"
                + "processPeriodValue : " + buyHistory.getProcessingPeriodValue() + "\n"
                + "enuriModelNo : " + buyHistory.getEnuriModelNo() + "\n"
                + "goodsName : " + buyHistory.getGoodsName() + "\n"
                + "goodsOptionValue : " + buyHistory.getGoodsOptionValue() + "\n"
                + "plNo : " + buyHistory.getPlNo() + "\n"
                + "smartDeliveryShoppingMallCode : " + buyHistory.getSmartDeliveryShoppingMallCode() + "\n"
                + "adjustedBuyQuantity : " + buyHistory.getAdjustedBuyQuantity() + "\n"
        );
        return "admin/search";
    }


}
