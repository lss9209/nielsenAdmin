package com.enuri.nielsen.admin.repository.shoppingDiary;

import com.enuri.nielsen.admin.domain.shoppingDiary.BuyHistory;
import com.enuri.nielsen.admin.domain.shoppingDiary.formDto.SearchInputForm;
import com.enuri.nielsen.admin.domain.shoppingDiary.viewDto.SearchResult;

public interface BuyHistoryRepositoryExtension {
    SearchResult searchWithQuery(SearchInputForm searchInputForm);
}
