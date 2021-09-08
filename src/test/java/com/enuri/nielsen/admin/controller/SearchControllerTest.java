package com.enuri.nielsen.admin.controller;

import com.enuri.nielsen.admin.domain.shoppingDiary.enums.Aggregation;
import com.enuri.nielsen.admin.domain.shoppingDiary.enums.Column;
import com.enuri.nielsen.admin.domain.shoppingDiary.enums.SearchMode;
import com.enuri.nielsen.admin.domain.shoppingDiary.formDto.SearchInputForm;
import com.enuri.nielsen.admin.domain.shoppingDiary.viewDto.SearchResult;
import com.enuri.nielsen.infra.MockMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcTest
class SearchControllerTest {

    @Autowired MockMvc mockMvc;

    @DisplayName("닐슨 구매 내역 데이터 조회 뷰 보여주는지 여부")
    @Test
    void showNielSenSearchView() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(view().name("admin/search"))
                .andExpect(status().isOk());
    }

    @DisplayName("닐슨 구매 내역 데이터 조회 조건 바인딩되는지 여부")
    @Test
    void checkCriterionBinding() throws Exception {
        String[] selectedColumns = {"ENR_MODEL_NO", "PL_NO", "CHANNEL"};

        SearchInputForm searchInputForm = new SearchInputForm();
        Set<Column> expectedSelectedColumns = new HashSet<>();
        expectedSelectedColumns.add(Column.ENR_MODEL_NO); expectedSelectedColumns.add(Column.PL_NO); expectedSelectedColumns.add(Column.CHANNEL);
        searchInputForm.setSelectedColumnSet(expectedSelectedColumns);
        searchInputForm.setGoodsName("상품명");
        searchInputForm.setGoodsOptionValue("옵션명");
        searchInputForm.setEnuriRepCateCode("11111");
        searchInputForm.setEnuriModelNo("");
        searchInputForm.setStartBuyDate(LocalDate.parse("2021-08-03"));
        searchInputForm.setEndBuyDate(LocalDate.parse("2021-08-03"));
        searchInputForm.setSortTargetColumn(null);
        searchInputForm.setAggregation(Aggregation.COUNT);
        searchInputForm.setAggregationTargetColumn(Column.ENR_MODEL_NO);
        searchInputForm.setSearchMode(SearchMode.AGGREGATION);

        mockMvc.perform(get("/admin/search")
                .param("selectedColumnSet", selectedColumns)
                .param("goodsName", "상품명")
                .param("goodsOptionValue", "옵션명")
                .param("enuriRepCateCode", "11111")
                .param("enuriModelNo", "")
                .param("startBuyDate", "2021-08-03")
                .param("endBuyDate","2021-08-03")
                .param("sortTargetColumn", "")
                .param("aggregation", "COUNT")
                .param("aggregationTargetColumn", "ENR_MODEL_NO")
        )
        .andExpect(model().attribute("searchInputForm", searchInputForm))
        .andExpect(view().name("admin/search"))
        .andExpect(status().isOk());
    }
}