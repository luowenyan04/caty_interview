package com.example.caty_interview.Entity;

import lombok.Data;

@Data
public class CoinDesk {
    Time time;
    String disclaimer;
    String chartName;
    Bpi bpi;
}
