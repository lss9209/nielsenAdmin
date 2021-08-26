package com.enuri.nielsen.admin.controller.shoppingDiary;

import com.enuri.nielsen.admin.domain.shoppingDiary.BuyHistoryRegisteredInShoppingDiary;
import com.enuri.nielsen.admin.domain.shoppingDiary.compositePrimaryKeyClass.BuyHistoryRegisteredInShoppingDiaryId;
import com.enuri.nielsen.admin.repository.shoppingDiary.BuyHistoryRegisteredInShoppingDiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {

    @Autowired
    BuyHistoryRegisteredInShoppingDiaryRepository buyHistoryRegisteredInShoppingDiaryRepository;

    @GetMapping("/")
    public String indexView() {
        return "index";
    }

    @GetMapping("/admin/search")
    public String adminMainView() {
        return "admin/search";
    }

    @GetMapping("/admin/test")
    public String test() {
        BuyHistoryRegisteredInShoppingDiaryId buyHistoryRegisteredInShoppingDiaryId = new BuyHistoryRegisteredInShoppingDiaryId(158, 9);
        BuyHistoryRegisteredInShoppingDiary buyHistoryRegisteredInShoppingDiary = buyHistoryRegisteredInShoppingDiaryRepository.findById(buyHistoryRegisteredInShoppingDiaryId).get();
        System.out.println("buyDate : " + buyHistoryRegisteredInShoppingDiary.getBuyDate() + "\n"
                + "processPeriodValue : " + buyHistoryRegisteredInShoppingDiary.getProcessingPeriodValue() + "\n"
                + "enuriModelNo : " + buyHistoryRegisteredInShoppingDiary.getEnuriModelNo() + "\n"
                + "goodsName : " + buyHistoryRegisteredInShoppingDiary.getGoodsName() + "\n"
                + "goodsOptionValue : " + buyHistoryRegisteredInShoppingDiary.getGoodsOptionValue() + "\n"
                + "plNo : " + buyHistoryRegisteredInShoppingDiary.getPlNo() + "\n"
                + "smartDeliveryShoppingMallCode : " + buyHistoryRegisteredInShoppingDiary.getSmartDeliveryShoppingMallCode() + "\n"
                + "adjustedBuyQuantity : " + buyHistoryRegisteredInShoppingDiary.getAdjustedBuyQuantity() + "\n"
        );
        return "admin/search";
    }
}
