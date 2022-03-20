package com.example.caty_interview.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Bpi {
    @JsonProperty("USD")
    Code USD;

    @JsonProperty("GBP")
    Code GBP;

    @JsonProperty("EUR")
    Code EUR;
}
