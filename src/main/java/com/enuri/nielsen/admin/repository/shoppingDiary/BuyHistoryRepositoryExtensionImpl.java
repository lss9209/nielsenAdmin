package com.enuri.nielsen.admin.repository.shoppingDiary;

import com.enuri.nielsen.admin.domain.shoppingDiary.*;
import com.enuri.nielsen.admin.domain.shoppingDiary.enums.Aggregation;
import com.enuri.nielsen.admin.domain.shoppingDiary.enums.Column;
import com.enuri.nielsen.admin.domain.shoppingDiary.enums.SearchMode;
import com.enuri.nielsen.admin.domain.shoppingDiary.formDto.SearchInputForm;
import com.enuri.nielsen.admin.domain.shoppingDiary.viewDto.SearchResult;
import com.enuri.nielsen.admin.exception.shoppingDiary.InvalidSumTargetException;
import com.enuri.nielsen.admin.exception.shoppingDiary.NotDefinedAggregationException;
import com.enuri.nielsen.admin.exception.shoppingDiary.NotDefinedAggregationTargetColumnException;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPQLQuery;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDate;

public class BuyHistoryRepositoryExtensionImpl extends QuerydslRepositorySupport implements BuyHistoryRepositoryExtension {

    QBuyHistory buyHistory = QBuyHistory.buyHistory;
    QModelMasterInfo modelMasterInfo = QModelMasterInfo.modelMasterInfo;
    QDateMasterInfo dateMasterInfo = QDateMasterInfo.dateMasterInfo;

    public BuyHistoryRepositoryExtensionImpl() {
        super(BuyHistory.class);
    }

    @Override
    public Page<SearchResult> search(SearchInputForm searchInputForm, Pageable pageable) {
        Page<SearchResult> searchResultList = null;
        if(searchInputForm.getSearchMode() == SearchMode.NORMAL) {
            searchResultList = searchWithNormalQuery(searchInputForm, pageable);
        } else if(searchInputForm.getSearchMode() == SearchMode.AGGREGATION) {
            try {
                searchResultList = searchWithAggregationQuery(searchInputForm, pageable);
            } catch (NotDefinedAggregationException notDefinedAggregationException) {
                System.out.println("Not Defined Aggregation, Check The Related Enum Class [com.enuri.nielsen.admin.domain.shoppingDiary.enums.Aggregation]");
            } catch (InvalidSumTargetException invalidSumTargetException) {
                System.out.println("The Target Column Selcted Is Not Available For Sum Operation");
            }
        }
        return searchResultList;
    }

    @Override
    public String getIndexDateWithGivenNormalDate(String normalDate) {
        JPQLQuery<String> query = from(dateMasterInfo).select(dateMasterInfo.indexDate).where(dateMasterInfo.date.eq(normalDate));
        String switchedIndexDate = query.fetchOne();
        return switchedIndexDate;
    }

    private Page<SearchResult> searchWithNormalQuery(SearchInputForm searchInputForm, Pageable pageable) {
        JPQLQuery<SearchResult> query = from(buyHistory)
                .select(Projections.constructor(SearchResult.class,
                        buyHistory.buyDate, buyHistory.processingPeriodValue, buyHistory.enuriModelNo,
                        buyHistory.goodsName, buyHistory.goodsOptionValue, buyHistory.plNo, buyHistory.smartDeliveryShoppingMallCode,
                        buyHistory.adjustedBuyQuantity))
                .where(buyHistory.deleteCode.eq("0")
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
                .leftJoin(modelMasterInfo).on(buyHistory.enuriModelNo.eq(modelMasterInfo.modelMasterInfoId.enuriModelNo)
                        .and(buyHistory.processingPeriodValue.eq(modelMasterInfo.modelMasterInfoId.processingPeriodValue)))
                .leftJoin(dateMasterInfo).on(buyHistory.buyDate.eq(dateMasterInfo.date));
        pageable = setSortCriterionToPageable(pageable, searchInputForm);
        JPQLQuery<SearchResult> pageableQuery = getQuerydsl().applyPagination(pageable, query);
        QueryResults<SearchResult> fetchResults = pageableQuery.fetchResults();
        return new PageImpl<>(fetchResults.getResults(), pageable, fetchResults.getTotal());
    }

    private Page<SearchResult> searchWithAggregationQuery(SearchInputForm searchInputForm, Pageable pageable) {
        JPQLQuery<SearchResult> query = from(buyHistory)
                .select(Projections.constructor(SearchResult.class, getAggregationResult(searchInputForm)))
                .where(buyHistory.deleteCode.eq("0")
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
                .leftJoin(modelMasterInfo).on(buyHistory.enuriModelNo.eq(modelMasterInfo.modelMasterInfoId.enuriModelNo)
                        .and(buyHistory.processingPeriodValue.eq(modelMasterInfo.modelMasterInfoId.processingPeriodValue)))
                .leftJoin(dateMasterInfo).on(buyHistory.buyDate.eq(dateMasterInfo.date));
        JPQLQuery<SearchResult> pageableQuery = getQuerydsl().applyPagination(pageable, query);
        QueryResults<SearchResult> fetchResults = pageableQuery.fetchResults();
        return new PageImpl<>(fetchResults.getResults(), pageable, fetchResults.getTotal());
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
        return buyHistory.enuriModelNo.eq(Long.parseLong(enuriModelNo.trim()));
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
        String startBuyDateStr = convertToNormalDateStrFormat(startBuyDate);
        String endBuyDateStr = convertToNormalDateStrFormat(endBuyDate);

        return buyHistory.buyDate.between(startBuyDateStr, endBuyDateStr);
    }

    private String convertToNormalDateStrFormat(LocalDate normalDate) {
        String normalDateStrBuilder = null;
        normalDateStrBuilder = normalDate.toString().replace("-", "");
        return normalDateStrBuilder;
    }

    private BooleanExpression betweenIndexDate(SearchInputForm searchInputForm) {
        LocalDate startIndexDate = searchInputForm.getStartIndexDate();
        LocalDate endIndexDate = searchInputForm.getEndIndexDate();

        if(startIndexDate == null || endIndexDate == null) return null;
        StringBuilder startIndexDateStrBuilder = convertToNielSenIndexDateStrFormat(startIndexDate);
        StringBuilder endIndexDateStrBuilder = convertToNielSenIndexDateStrFormat(endIndexDate);

        return dateMasterInfo.indexDate.between(startIndexDateStrBuilder.toString(), endIndexDateStrBuilder.toString());
    }

    private StringBuilder convertToNielSenIndexDateStrFormat(LocalDate indexDate) {
        StringBuilder indexDateStrBuilder = new StringBuilder();
        indexDateStrBuilder.append(indexDate.toString().substring(0,4)).append("14").append(indexDate.toString().substring(5,7));
        return indexDateStrBuilder;
    }

    private Pageable setSortCriterionToPageable(Pageable pageable, SearchInputForm searchInputForm) {
        if(searchInputForm.getSortTargetColumn() == Column.ENR_MODEL_NO) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.ASC, "enuriModelNo");
        } else if(searchInputForm.getSortTargetColumn() == Column.PL_NO) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.ASC, "plNo");
        }
        return pageable;
    }

    private NumberExpression getAggregationResult(SearchInputForm searchInputForm) throws NotDefinedAggregationException, InvalidSumTargetException {

        Path aggregationTargetColumn = null;
        try {
            aggregationTargetColumn = getAggregationTargetColumn(searchInputForm);
        } catch (NotDefinedAggregationTargetColumnException notDefinedAggregationTargetColumnException) {
            System.out.println("Not Defined AggregationTargetColumn");
        }

        if(searchInputForm.getAggregation().equals(Aggregation.SUM)) {
            if(aggregationTargetColumn instanceof NumberPath) {
                return ((NumberPath) aggregationTargetColumn).sum();
            }else if(aggregationTargetColumn instanceof StringPath) {
                throw new InvalidSumTargetException();
            }
        }else if(searchInputForm.getAggregation().equals(Aggregation.COUNT)) {
            if(aggregationTargetColumn instanceof NumberPath) {
                return ((NumberPath) aggregationTargetColumn).count();
            } else if(aggregationTargetColumn instanceof StringPath) {
                return ((StringPath) aggregationTargetColumn).count();
            }
        }else if(searchInputForm.getAggregation().equals(Aggregation.COUNT_DISTINCT)) {
            if(aggregationTargetColumn instanceof NumberPath) {
                return ((NumberPath) aggregationTargetColumn).countDistinct();
            } else if(aggregationTargetColumn instanceof StringPath) {
                return ((StringPath) aggregationTargetColumn).countDistinct();
            }
        }
        throw new NotDefinedAggregationException();
    }

    private Path getAggregationTargetColumn(SearchInputForm searchInputForm) throws NotDefinedAggregationTargetColumnException {
        if(searchInputForm.getAggregationTargetColumn() == Column.ENR_MODEL_NO) {
            return buyHistory.enuriModelNo;
        } else if(searchInputForm.getAggregationTargetColumn() == Column.PL_NO) {
            return buyHistory.plNo;
        }
        throw new NotDefinedAggregationTargetColumnException();
    }
}
