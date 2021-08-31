package com.enuri.nielsen.admin.repository.shoppingDiary;

import com.enuri.nielsen.admin.domain.shoppingDiary.BuyHistory;
import com.enuri.nielsen.admin.domain.shoppingDiary.QBuyHistory;
import com.enuri.nielsen.admin.domain.shoppingDiary.QDateMasterInfo;
import com.enuri.nielsen.admin.domain.shoppingDiary.QModelMasterInfo;
import com.enuri.nielsen.admin.domain.shoppingDiary.enums.Column;
import com.enuri.nielsen.admin.domain.shoppingDiary.enums.SearchMode;
import com.enuri.nielsen.admin.domain.shoppingDiary.formDto.SearchInputForm;
import com.enuri.nielsen.admin.domain.shoppingDiary.viewDto.SearchResult;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPQLQuery;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BuyHistoryRepositoryExtensionImpl extends QuerydslRepositorySupport implements BuyHistoryRepositoryExtension {

    QBuyHistory buyHistory = QBuyHistory.buyHistory;
    QModelMasterInfo modelMasterInfo = QModelMasterInfo.modelMasterInfo;
    QDateMasterInfo dateMasterInfo = QDateMasterInfo.dateMasterInfo;

    public BuyHistoryRepositoryExtensionImpl() {
        super(BuyHistory.class);
    }

    @Override
    public List<SearchResult> search(SearchInputForm searchInputForm) {
        List<SearchResult> searchResultList = new ArrayList<>();
        if(searchInputForm.getSearchMode() == SearchMode.NORMAL) {
            searchResultList = searchWithNormalQuery(searchInputForm);
        } else if(searchInputForm.getSearchMode() == SearchMode.AGGREGATION) {
            searchResultList = searchWithAggregationQuery(searchInputForm);
        }
        return searchResultList;
    }

    private List<SearchResult> searchWithNormalQuery(SearchInputForm searchInputForm) {
        JPQLQuery<SearchResult> query = from(buyHistory)
                .select(Projections.constructor(SearchResult.class,
                        buyHistory.buyDate, buyHistory.processingPeriodValue, buyHistory.enuriModelNo,
                        buyHistory.goodsName, buyHistory.goodsOptionValue, buyHistory.plNo, buyHistory.smartDeliveryShoppingMallCode,
                        buyHistory.adjustedBuyQuantity))
                .where(buyHistory.deleteCode.eq('0')
                        .and(buyHistory.payAmountExcludingShippingFee.gt(0L))
                        .and(buyHistory.buyDate.eq("20210701"))
                        .and(containsGoodsName(searchInputForm))
                        .and(eqGoodsOptionValue(searchInputForm))
                        .and(eqEnuriRepCateCode(searchInputForm))
                        .and(eqEnuriModelNo(searchInputForm))
                        .and(eqPlNo(searchInputForm))
                        .and(betweenBuyDate(searchInputForm))
                        .and(betweenIndexDate(searchInputForm))
                )
                .join(modelMasterInfo).on(buyHistory.enuriModelNo.eq(modelMasterInfo.modelMasterInfoId.enuriModelNo)
                        .and(buyHistory.processingPeriodValue.eq(modelMasterInfo.modelMasterInfoId.processingPeriodValue)))
                .join(dateMasterInfo).on(buyHistory.buyDate.eq(dateMasterInfo.date))
                .orderBy(getSortCriterion(searchInputForm))
                .limit(20);
        return query.fetch();
    }



    private List<SearchResult> searchWithAggregationQuery(SearchInputForm searchInputForm) {
        return null;
    }

    private BooleanExpression containsGoodsName(SearchInputForm searchInputForm) {
        String goodsName = searchInputForm.getGoodsName();
        if(goodsName == null || Strings.isEmpty(goodsName)) return null;
        return buyHistory.goodsName.contains(goodsName);
    }

    private BooleanExpression eqGoodsOptionValue(SearchInputForm searchInputForm) {
        String goodsOptionValue = searchInputForm.getGoodsOptionValue();
        if(goodsOptionValue == null || Strings.isEmpty(goodsOptionValue)) return null;
        return buyHistory.goodsOptionValue.eq(goodsOptionValue);
    }

    private BooleanExpression eqEnuriRepCateCode(SearchInputForm searchInputForm) {
        String enuriCateCode = searchInputForm.getEnuriRepCateCode();
        if(enuriCateCode == null || Strings.isEmpty(enuriCateCode)) return null;
        return buyHistory.enuriCateCode.eq(enuriCateCode);
    }

    private BooleanExpression eqEnuriModelNo(SearchInputForm searchInputForm) {
        String enuriModelNo = searchInputForm.getEnuriModelNo();
        if(enuriModelNo == null || Strings.isEmpty(enuriModelNo)) return null;
        return buyHistory.enuriModelNo.eq(Integer.parseInt(enuriModelNo.trim()));
    }

    private BooleanExpression eqPlNo(SearchInputForm searchInputForm) {
        String plNo = searchInputForm.getPlNo();
        if(plNo == null || Strings.isEmpty(plNo)) return null;
        return buyHistory.plNo.eq(Long.parseLong(plNo.trim()));
    }

    private BooleanExpression betweenBuyDate(SearchInputForm searchInputForm) {
        LocalDate startBuyDate = searchInputForm.getStartBuyDate();
        LocalDate endBuyDate = searchInputForm.getEndBuyDate();
        if(startBuyDate == null || endBuyDate == null) return null;
        System.out.println("시작날짜 문자열 : " + startBuyDate.toString() + "끝날짜 문자열 : " + endBuyDate.toString());
        return buyHistory.buyDate.between(startBuyDate.toString(), endBuyDate.toString());
    }

    private BooleanExpression betweenIndexDate(SearchInputForm searchInputForm) {
        LocalDate startIndexDate = searchInputForm.getStartIndexDate();
        LocalDate endIndexDate = searchInputForm.getEndIndexDate();
        if(startIndexDate == null || endIndexDate == null) return null;
        return dateMasterInfo.indexDate.between(startIndexDate.toString(), endIndexDate.toString());
    }

    private OrderSpecifier getSortCriterion(SearchInputForm searchInputForm) {
        if(searchInputForm.getSortTargetColumn() == null) {
            return buyHistory.buyHistoryId.integrationBuyNo.asc();
        } else if(searchInputForm.getSortTargetColumn() == Column.ENR_MODEL_NO) {
            return buyHistory.enuriModelNo.asc();
        } else if(searchInputForm.getSortTargetColumn() == Column.PL_NO) {
            return buyHistory.plNo.asc();
        } else return buyHistory.buyHistoryId.integrationBuyNo.asc();
    }

    private boolean allColumnsChecked(SearchInputForm searchInputForm) {
        return searchInputForm.getSelectedColumnList().isEmpty();
    }
}
