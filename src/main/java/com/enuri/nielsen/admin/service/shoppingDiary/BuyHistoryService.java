package com.enuri.nielsen.admin.service.shoppingDiary;

import com.enuri.nielsen.admin.domain.shoppingDiary.BuyHistory;
import com.enuri.nielsen.admin.domain.shoppingDiary.enums.Aggregation;
import com.enuri.nielsen.admin.domain.shoppingDiary.formDto.SearchInputForm;
import com.enuri.nielsen.admin.domain.shoppingDiary.viewDto.SearchResult;
import com.enuri.nielsen.admin.repository.shoppingDiary.BuyHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BuyHistoryService {

    @Autowired
    BuyHistoryRepository buyHistoryRepository;

    public SearchResult search(SearchInputForm searchInputForm) {
        SearchResult searchResult = buyHistoryRepository.searchWithQuery(searchInputForm);
        return null;
    }

    private boolean isNormalQuerySearch(Aggregation aggregation) {
        return aggregation == null;
    }

    private boolean isAggregationQuerySearch(Aggregation aggregation) {
        return aggregation != null;
    }
}
