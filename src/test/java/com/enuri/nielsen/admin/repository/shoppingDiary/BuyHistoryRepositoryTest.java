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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@MockMvcTest
class BuyHistoryRepositoryTest {

    @Autowired MockMvc mockMvc;
    @Autowired BuyHistoryRepository buyHistoryRepository;

    @DisplayName("NORMAL 쿼리로 서치했을 때 정렬/페이징이 잘 동작하는지 여부")
    @Test
    void checkNormalQuerySortAndPaging() throws Exception {

        SearchInputForm searchInputForm = new SearchInputForm();
        searchInputForm.setSortTargetColumn(Column.PL_NO);
        searchInputForm.setSearchMode(SearchMode.NORMAL);

        Page<SearchResult> searchResultPage = buyHistoryRepository.search(searchInputForm, PageRequest.of(4, 20));

        assertEquals(searchResultPage.getContent().get(5).getGoodsName(), "[일동] 후디스 프리미엄 산양분유 1단계(태어나서 6개월까지조제분유) 800g");
    }

    @DisplayName("NORMAL 쿼리로 서치했을 때 조건검색이 잘 동작하는지 여부")
    @Test
    void checkNormalQueryWhereClause() throws Exception {

        SearchInputForm searchInputForm = new SearchInputForm();
        searchInputForm.setGoodsName("피코크");
        searchInputForm.setSearchMode(SearchMode.NORMAL);

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

        Page<SearchResult> searchResultPage = buyHistoryRepository.search(searchInputForm, PageRequest.of(0, 20));

        assertEquals(searchResultPage.getContent().get(0).getAggregationResult(), 24226L);
    }

}