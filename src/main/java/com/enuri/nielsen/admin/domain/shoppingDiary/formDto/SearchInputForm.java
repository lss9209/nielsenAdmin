package com.enuri.nielsen.admin.domain.shoppingDiary.formDto;

import com.enuri.nielsen.admin.domain.shoppingDiary.enums.Aggregation;
import com.enuri.nielsen.admin.domain.shoppingDiary.enums.Column;
import com.enuri.nielsen.admin.domain.shoppingDiary.enums.SearchMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor @AllArgsConstructor
public class SearchInputForm {

    private SearchMode searchMode;

    private Set<Column> selectedColumnSet = new HashSet<>();

    private String goodsName;

    private String goodsOptionValue;

    private String enuriRepCateCode;

    private String enuriModelNo;

    private String plNo;

    private LocalDate startBuyDate;

    private LocalDate endBuyDate;

    private LocalDate startIndexDate;

    private LocalDate endIndexDate;

    private Column sortTargetColumn;

    private Aggregation aggregation;

    private Column aggregationTargetColumn;

    public String toString() {
        return "selectedColumnSet : " + selectedColumnSet + ", goodsName : " + goodsName + ", goodsOptionValue : " + goodsOptionValue +  ", enuriRepCateCode : " + enuriRepCateCode + ", enuriModelNo : " + enuriModelNo
                + ", plNo : " + plNo + ", startBuyDate : " + startBuyDate + ", endBuyDate : " + endBuyDate + ", startIndexDate : " + startIndexDate
                + ", endIndexDate : " + endIndexDate + ", sortTargetColumn : " + sortTargetColumn + ", aggregation : " + aggregation + ", aggregationTargetColumn : " + aggregationTargetColumn;
    }
}
