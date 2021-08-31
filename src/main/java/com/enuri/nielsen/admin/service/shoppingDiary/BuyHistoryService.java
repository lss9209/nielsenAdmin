package com.enuri.nielsen.admin.service.shoppingDiary;

import com.enuri.nielsen.admin.domain.shoppingDiary.BuyHistory;
import com.enuri.nielsen.admin.domain.shoppingDiary.enums.Aggregation;
import com.enuri.nielsen.admin.domain.shoppingDiary.enums.SearchMode;
import com.enuri.nielsen.admin.domain.shoppingDiary.formDto.SearchInputForm;
import com.enuri.nielsen.admin.domain.shoppingDiary.viewDto.SearchResult;
import com.enuri.nielsen.admin.repository.shoppingDiary.BuyHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BuyHistoryService {

    @Autowired
    BuyHistoryRepository buyHistoryRepository;

    public List<SearchResult> search(SearchInputForm searchInputForm) {
        checkIfAggregationQuerySearch(searchInputForm);
        List<SearchResult> searchResult = buyHistoryRepository.search(searchInputForm);
        return searchResult;
    }

    private void checkIfAggregationQuerySearch(SearchInputForm searchInputForm) {
        if(searchInputForm.getAggregation() != null) {
            searchInputForm.setSearchMode(SearchMode.AGGREGATION);
        } else {
            searchInputForm.setSearchMode(SearchMode.NORMAL);
        }
    }
}
