package com.example.caty_interview.DTO;

import lombok.Data;

import javax.persistence.Column;

@Data
public class CurrencyDTO {
    @Column(name = "name")
    String name;

    @Column(name = "name_zh")
    String nameZh;

    @Column(name = "rate")
    String rate;
}
