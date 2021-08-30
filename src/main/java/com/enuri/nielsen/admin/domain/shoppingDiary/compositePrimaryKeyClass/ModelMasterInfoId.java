package com.enuri.nielsen.admin.domain.shoppingDiary.compositePrimaryKeyClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor @AllArgsConstructor
@Embeddable
public class ModelMasterInfoId implements Serializable {

    @Column(name = "PROC_PRD_VLU", nullable = false)
    private String processingPeriodValue;

    @Column(name = "ENR_MODEL_NO", nullable = false)
    private int enuriModelNo;
}
