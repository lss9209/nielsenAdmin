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

    private SearchMode searchMode;

    private String buyDate;

    private String processingPeriodValue;

    private int enuriModelNo;

    private String goodsName;

    private String goodsOptionValue;

    private long plNo;

    private int smartDeliveryShoppingMallCode;

    private int adjustedBuyQuantity;

    private long aggregationResult;
}
