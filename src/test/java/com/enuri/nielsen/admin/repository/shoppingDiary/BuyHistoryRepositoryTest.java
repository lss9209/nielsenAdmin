package com.enuri.nielsen.admin.repository.shoppingDiary;

import com.enuri.nielsen.admin.domain.shoppingDiary.enums.Aggregation;
import com.enuri.nielsen.admin.domain.shoppingDiary.enums.Column;
import com.enuri.nielsen.admin.domain.shoppingDiary.enums.SearchMode;
import com.enuri.nielsen.admin.domain.shoppingDiary.formDto.SearchInputForm;
import com.enuri.nielsen.admin.domain.shoppingDiary.viewDto.SearchResult;
import com.enuri.nielsen.admin.service.shoppingDiary.BuyHistoryService;
import com.enuri.nielsen.infra.MockMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@MockMvcTest
class BuyHistoryRepositoryTest {

    @Autowired MockMvc mockMvc;
    @Autowired BuyHistoryRepository buyHistoryRepository;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate startBuyDate = LocalDate.parse("2021-07-01", formatter);
    LocalDate endBuyDate = LocalDate.parse("2021-07-01", formatter);

    @DisplayName("NORMAL 쿼리로 서치했을 때 정렬/페이징이 잘 동작하는지 여부")
    @Test
    void checkNormalQuerySortAndPaging() throws Exception {

        SearchInputForm searchInputForm = new SearchInputForm();
        searchInputForm.setSortTargetColumn(Column.PL_NO);
        searchInputForm.setSearchMode(SearchMode.NORMAL);
        searchInputForm.setStartBuyDate(startBuyDate);
        searchInputForm.setEndBuyDate(endBuyDate);

        Page<SearchResult> searchResultPage = buyHistoryRepository.search(searchInputForm, PageRequest.of(4, 20));

        assertEquals(searchResultPage.getContent().get(5).getGoodsName(), "[일동] 후디스 프리미엄 산양분유 1단계(태어나서 6개월까지조제분유) 800g");
    }

    @DisplayName("NORMAL 쿼리로 서치했을 때 조건검색이 잘 동작하는지 여부")
    @Test
    void checkNormalQueryWhereClause() throws Exception {

        SearchInputForm searchInputForm = new SearchInputForm();
        searchInputForm.setGoodsName("피코크");
        searchInputForm.setSearchMode(SearchMode.NORMAL);
        searchInputForm.setStartBuyDate(startBuyDate);
        searchInputForm.setEndBuyDate(endBuyDate);

        Page<SearchResult> searchResultPage = buyHistoryRepository.search(searchInputForm, PageRequest.of(0, 20));

        assertEquals(searchResultPage.getContent().get(2).getGoodsName(), "피코크 유기농 시원한찬물 제주녹차 50입");
    }

    @DisplayName("AGGREGATION 쿼리로 서치했을 때 집계검색이 잘 동작하는지 여부")
    @Test
    void checkAggregationQuery() throws Exception {

        SearchInputForm searchInputForm = new SearchInputForm();
        searchInputForm.setAggregation(Aggregation.COUNT_DISTINCT);
        searchInputForm.setAggregationTargetColumn(Column.ENR_MODEL_NO);
        searchInputForm.setSearchMode(SearchMode.AGGREGATION);
        searchInputForm.setStartBuyDate(startBuyDate);
        searchInputForm.setEndBuyDate(endBuyDate);

        Page<SearchResult> searchResultPage = buyHistoryRepository.search(searchInputForm, PageRequest.of(0, 20));

        assertEquals(searchResultPage.getContent().get(0).getAggregationResult(), 24226L);
    }

    @DisplayName("날짜검색(Normal, Index) 정확성 여부")
    @Test
    void checkDateCriterion() throws Exception {

        SearchInputForm searchInputFormWithNormalDateCriterion = new SearchInputForm();
        searchInputFormWithNormalDateCriterion.setSearchMode(SearchMode.NORMAL);
        searchInputFormWithNormalDateCriterion.setStartBuyDate(LocalDate.parse("2021-06-28", formatter));
        searchInputFormWithNormalDateCriterion.setEndBuyDate(LocalDate.parse("2021-08-01", formatter));

        Page<SearchResult> searchResultPageByNormalDateCriterion = buyHistoryRepository.search(searchInputFormWithNormalDateCriterion, Pageable.unpaged());
        int contentSizeWhenSearchedByNormalDate = searchResultPageByNormalDateCriterion.getContent().size();

        SearchInputForm searchInputFormWithIndexDateConvertedFromAboveNormalDateCriterion = new SearchInputForm();
        searchInputFormWithIndexDateConvertedFromAboveNormalDateCriterion.setSearchMode(SearchMode.NORMAL);
        searchInputFormWithIndexDateConvertedFromAboveNormalDateCriterion.setStartIndexDate(LocalDate.parse("2021-07-15", formatter));
        searchInputFormWithIndexDateConvertedFromAboveNormalDateCriterion.setEndIndexDate(LocalDate.parse("2021-07-15", formatter));

        Page<SearchResult> searchResultPageByIndexDateConvertedFromAboveNormalDateCriterion
                = buyHistoryRepository.search(searchInputFormWithIndexDateConvertedFromAboveNormalDateCriterion, Pageable.unpaged());
        int contentSizeWhenSearchedByIndexDateConvertedFromAboveNormalDateCriterion
                = searchResultPageByIndexDateConvertedFromAboveNormalDateCriterion.getContent().size();

        assertEquals(contentSizeWhenSearchedByNormalDate, contentSizeWhenSearchedByIndexDateConvertedFromAboveNormalDateCriterion);
    }

}