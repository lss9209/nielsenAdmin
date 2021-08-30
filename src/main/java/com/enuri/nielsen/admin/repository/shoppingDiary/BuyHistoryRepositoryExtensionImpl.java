package com.enuri.nielsen.admin.repository.shoppingDiary;

import com.enuri.nielsen.admin.domain.shoppingDiary.BuyHistory;
import com.enuri.nielsen.admin.domain.shoppingDiary.formDto.SearchInputForm;
import com.enuri.nielsen.admin.domain.shoppingDiary.viewDto.SearchResult;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class BuyHistoryRepositoryExtensionImpl extends QuerydslRepositorySupport implements BuyHistoryRepositoryExtension {

    public BuyHistoryRepositoryExtensionImpl() {
        super(BuyHistory.class);
    }

    @Override
    public SearchResult searchWithQuery(SearchInputForm searchInputForm) {
        return null;
    }
}
