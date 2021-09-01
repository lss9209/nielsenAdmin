package com.enuri.nielsen.admin.service.shoppingDiary;

import com.enuri.nielsen.admin.domain.shoppingDiary.enums.Column;
import com.enuri.nielsen.admin.domain.shoppingDiary.enums.SearchMode;
import com.enuri.nielsen.admin.domain.shoppingDiary.formDto.SearchInputForm;
import com.enuri.nielsen.admin.domain.shoppingDiary.viewDto.SearchResult;
import com.enuri.nielsen.admin.repository.shoppingDiary.BuyHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BuyHistoryService {

    @Autowired
    BuyHistoryRepository buyHistoryRepository;

    public Page<SearchResult> search(SearchInputForm searchInputForm, Pageable pageable) {
        checkIfAggregationQuerySearch(searchInputForm);
        checkAllIfNothingSelected(searchInputForm);
        Page<SearchResult> searchResult = buyHistoryRepository.search(searchInputForm, pageable);
        return searchResult;
    }

    private void checkIfAggregationQuerySearch(SearchInputForm searchInputForm) {
        if(searchInputForm.getAggregation() != null) {
            searchInputForm.setSearchMode(SearchMode.AGGREGATION);
        } else {
            searchInputForm.setSearchMode(SearchMode.NORMAL);
        }
    }

    private void checkAllIfNothingSelected(SearchInputForm searchInputForm) {
        Set<Column> selectedColumnsSet = searchInputForm.getSelectedColumnSet();
        if(selectedColumnsSet.size() == 0) {
            for(Column column : Column.values()) {
                selectedColumnsSet.add(column);
            }
        }
    }
}
