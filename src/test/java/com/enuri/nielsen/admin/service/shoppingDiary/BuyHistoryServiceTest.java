package com.enuri.nielsen.admin.service.shoppingDiary;

import com.enuri.nielsen.admin.domain.shoppingDiary.enums.Aggregation;
import com.enuri.nielsen.admin.domain.shoppingDiary.enums.Column;
import com.enuri.nielsen.admin.domain.shoppingDiary.enums.SearchMode;
import com.enuri.nielsen.admin.domain.shoppingDiary.formDto.SearchInputForm;
import com.enuri.nielsen.admin.repository.shoppingDiary.BuyHistoryRepository;
import com.enuri.nielsen.infra.MockMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcTest
class BuyHistoryServiceTest {

    @Autowired MockMvc mockMvc;
    @Autowired BuyHistoryService buyHistoryService;

    @DisplayName("집계 조건 여부에 따라 서치모드가 알맞게 변경되는지 여부")
    @Test
    void checkSearchModeDetermining() throws Exception {

        String[] selectedColumns = {"ENR_MODEL_NO", "PL_NO", "CHANNEL"};

        SearchInputForm searchInputForm = new SearchInputForm();
        Set<Column> expectedSelectedColumns = new HashSet<>();
        expectedSelectedColumns.add(Column.ENR_MODEL_NO); expectedSelectedColumns.add(Column.PL_NO); expectedSelectedColumns.add(Column.CHANNEL);
        searchInputForm.setSelectedColumnSet(expectedSelectedColumns);
        searchInputForm.setSortTargetColumn(null);
        searchInputForm.setAggregation(Aggregation.COUNT);
        searchInputForm.setAggregationTargetColumn(Column.ENR_MODEL_NO);

        buyHistoryService.search(searchInputForm, Pageable.ofSize(20));

        assertEquals(SearchMode.AGGREGATION, searchInputForm.getSearchMode());

        searchInputForm.setAggregation(null);

        buyHistoryService.search(searchInputForm, Pageable.ofSize(20));

        assertEquals(SearchMode.NORMAL, searchInputForm.getSearchMode());
    }

    @DisplayName("아무 SELECT 칼럼 체크 없을 때 모든 칼럼을 체크하는지 여부")
    @Test
    void checkAllSelectedWhenNothingSelected() throws Exception {

        String[] selectedColumns = {"ENR_MODEL_NO", "PL_NO", "CHANNEL"};

        SearchInputForm searchInputForm = new SearchInputForm();
        searchInputForm.setGoodsName("상품명");
        searchInputForm.setGoodsOptionValue("옵션명");
        searchInputForm.setEnuriRepCateCode("11111");
        searchInputForm.setEnuriModelNo("");
        searchInputForm.setStartBuyDate(LocalDate.parse("2021-08-03"));
        searchInputForm.setSortTargetColumn(null);
        searchInputForm.setAggregation(Aggregation.COUNT);
        searchInputForm.setAggregationTargetColumn(Column.ENR_MODEL_NO);
        searchInputForm.setSearchMode(SearchMode.AGGREGATION);

        buyHistoryService.search(searchInputForm, Pageable.ofSize(20));

        assertTrue(searchInputForm.getSelectedColumnSet().size() == Column.values().length);
    }
}