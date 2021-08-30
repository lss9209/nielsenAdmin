package com.enuri.nielsen.admin.domain.shoppingDiary.formDto;

import com.enuri.nielsen.admin.domain.shoppingDiary.enums.Aggregation;
import com.enuri.nielsen.admin.domain.shoppingDiary.enums.Column;
import com.enuri.nielsen.admin.domain.shoppingDiary.enums.DateCriterion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor @AllArgsConstructor
public class SearchInputForm {

    private Set<Column> selectedColumnList = new HashSet<>();

    private String goodsName;

    private String goodsOptionValue;

    private String enuriRepCateCode;

    private String enuriModelNo;

    private String plNo;

    private LocalDate startBuyDate;

    private LocalDate endBuyDate;

    private DateCriterion dateCriterion;

    private LocalDate startDateByDateCriterion;

    private LocalDate endDateByDateCriterion;

    private Column sortTargetColumn;

    private Aggregation aggregation;

    private Column aggregationTargetColumn;

    public String toString() {
        return "selectedColumnList : " + selectedColumnList + ", goodsName : " + goodsName + ", goodsOptionValue : " + goodsOptionValue +  ", enuriRepCateCode : " + enuriRepCateCode + ", enuriModelNo : " + enuriModelNo
                + ", plNo : " + plNo + ", startBuyDate : " + startBuyDate + ", endBuyDate : " + endBuyDate + ", dateCriterion : " + dateCriterion + ", startDateByDateCriterion : " + startDateByDateCriterion
                + ", endDateByDateCriterion : " + endDateByDateCriterion + ", sortTargetColumn : " + sortTargetColumn + ", aggregation : " + aggregation + ", aggregationTargetColumn : " + aggregationTargetColumn;
    }
}
