package com.enuri.nielsen.admin.domain.shoppingDiary.compositePrimaryKeyClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigInteger;

@Data
@NoArgsConstructor @AllArgsConstructor
@Embeddable
public class BuyHistoryRegisteredInShoppingDiaryId implements Serializable {

    @Column(name = "ITG_BUY_NO", nullable = false)
    private long integrationBuyNo;

    @Column(name = "SRC_TBL_ID", nullable = false)
    private int sourceTableId;
}
