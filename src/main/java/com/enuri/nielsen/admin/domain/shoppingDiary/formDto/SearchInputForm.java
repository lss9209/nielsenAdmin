package com.enuri.nielsen.admin.domain.shoppingDiary.formDto;

import com.enuri.nielsen.admin.domain.shoppingDiary.enums.Aggregation;
import com.enuri.nielsen.admin.domain.shoppingDiary.enums.Column;
import com.enuri.nielsen.admin.domain.shoppingDiary.enums.DateCriterion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
public class SearchInputForm {

    private List<Column> selectedColumnList = new ArrayList<>();

    private String goodsName;

    private String goodsOptionValue;

    private String enuriRepCateCode;

    private int enuriModelNo;

    private long plNo;

    private Date startBuyDate;

    private Date endBuyDate;

    private DateCriterion dateCriterion;

    private Date startDateByDateCriterion;

    private Date endDateByDateCriterion;

    private Column sortTargetColumn;

    private Aggregation aggregation;

    private Column sumTargetColumn;
}
