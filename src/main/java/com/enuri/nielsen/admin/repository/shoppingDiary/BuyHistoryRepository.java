package com.enuri.nielsen.admin.repository.shoppingDiary;

import com.enuri.nielsen.admin.domain.shoppingDiary.BuyHistory;
import com.enuri.nielsen.admin.domain.shoppingDiary.compositePrimaryKeyClass.BuyHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyHistoryRepository extends BuyHistoryRepositoryExtension, JpaRepository<BuyHistory, BuyHistoryId> {
}
