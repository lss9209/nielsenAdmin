package com.enuri.nielsen.admin.domain.shoppingDiary;

import com.enuri.nielsen.admin.domain.shoppingDiary.compositePrimaryKeyClass.BuyHistoryId;
import lombok.*;

import javax.persistence.*;

@Entity(name = "M_MONTH_DF_BUY_TRN")
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class BuyHistory {

    @EmbeddedId
    BuyHistoryId buyHistoryId;

    @Column(name = "BUY_DATE")
    private String buyDate;

    @Column(name = "PROC_PRD_VLU")
    private String processingPeriodValue;

    @Column(name = "ENR_MODEL_NO")
    private int enuriModelNo;

    @Column(name = "GOODS_NAME")
    private String goodsName;

    @Column(name = "GOODS_OPTN_VLU")
    private String goodsOptionValue;

    @Column(name = "PL_NO")
    private long plNo;

    @Column(name = "SMTD_SHOP_CODE")
    private int smartDeliveryShoppingMallCode;

    @Column(name = "ADJ_BUY_QNTY")
    private int adjustedBuyQuantity;

    @Column(name = "DEL_CODE")
    private char deleteCode;

    @Column(name = "DRM_PAY_AMT")
    private long payAmountExcludingShippingFee;

    @Column(name = "ENR_REP_CATE_CODE")
    private String enuriCateCode;
}
