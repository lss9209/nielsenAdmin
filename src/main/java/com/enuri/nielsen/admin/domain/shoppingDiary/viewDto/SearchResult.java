package com.enuri.nielsen.admin.domain.shoppingDiary.viewDto;

import com.enuri.nielsen.admin.domain.shoppingDiary.compositePrimaryKeyClass.BuyHistoryId;
import com.enuri.nielsen.admin.domain.shoppingDiary.enums.SearchMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;

@Data
@NoArgsConstructor @AllArgsConstructor
public class SearchResult {

    private String buyDate;

    private String processingPeriodValue;

    private int enuriModelNo;

    private String goodsName;

    private String goodsOptionValue;

    private long plNo;

    private int smartDeliveryShoppingMallCode;

    private int adjustedBuyQuantity;

    private long aggregationResult;

    public SearchResult(String buyDate, String processingPeriodValue, int enuriModelNo, String goodsName, String goodsOptionValue,
                        long plNo, int smartDeliveryShoppingMallCode, int adjustedBuyQuantity) {
        this.buyDate = buyDate;
        this.processingPeriodValue = processingPeriodValue;
        this.enuriModelNo = enuriModelNo;
        this.goodsName = goodsName;
        this.goodsOptionValue = goodsOptionValue;
        this.plNo = plNo;
        this.smartDeliveryShoppingMallCode = smartDeliveryShoppingMallCode;
        this.adjustedBuyQuantity = adjustedBuyQuantity;
    }
}
