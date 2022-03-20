package com.example.caty_interview.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "currency")
@Data
public class CurrencyEntity {
    @Id
    @Column(name = "name")
    String name;

    @Column(name = "name_zh")
    String nameZh;

    @Column(name = "rate")
    String rate;

    @UpdateTimestamp
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss",timezone = "Asia/Taipei")
    @Column(name = "updated_time")
    Date updatedTime;
}
