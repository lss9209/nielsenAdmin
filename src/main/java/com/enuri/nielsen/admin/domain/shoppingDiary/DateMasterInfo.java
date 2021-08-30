package com.enuri.nielsen.admin.domain.shoppingDiary;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "M_DATE_BSC")
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class DateMasterInfo {

    @Id @Column(name = "DATE")
    private String date;
}
