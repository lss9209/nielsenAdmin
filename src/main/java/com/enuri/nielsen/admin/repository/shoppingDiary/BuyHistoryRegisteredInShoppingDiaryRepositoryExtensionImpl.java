package com.enuri.nielsen.admin.repository.shoppingDiary;

import com.enuri.nielsen.admin.domain.shoppingDiary.BuyHistoryRegisteredInShoppingDiary;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

public class BuyHistoryRegisteredInShoppingDiaryRepositoryExtensionImpl extends QuerydslRepositorySupport implements BuyHistoryRegisteredInShoppingDiaryRepositoryExtension {

    public BuyHistoryRegisteredInShoppingDiaryRepositoryExtensionImpl() {
        super(BuyHistoryRegisteredInShoppingDiary.class);
    }
}
