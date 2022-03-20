package com.example.caty_interview.Entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;

@Data
public class Time {
    @Column(name = "updated")
    String updated;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "updatedISO")
    Date updatedISO;

    @Column(name = "updateduk")
    String updateduk;
}
