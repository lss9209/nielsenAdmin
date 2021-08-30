package com.enuri.nielsen.admin.domain.shoppingDiary;

import com.enuri.nielsen.admin.domain.shoppingDiary.compositePrimaryKeyClass.ModelMasterInfoId;
import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity(name = "M_GOODS_BSC")
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class ModelMasterInfo {

    @EmbeddedId
    ModelMasterInfoId modelMasterInfoId;
}
