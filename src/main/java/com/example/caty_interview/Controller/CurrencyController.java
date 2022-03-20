package com.example.caty_interview.Controller;

import com.example.caty_interview.Entity.Bpi;
import com.example.caty_interview.Entity.Code;
import com.example.caty_interview.Entity.CoinDesk;
import com.example.caty_interview.Entity.CurrencyEntity;
import com.example.caty_interview.Service.CurrencyService;
import com.example.caty_interview.Tool.CurrencyScan;
import com.example.caty_interview.Tool.JavaScriptMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CurrencyScan currencyScan;

    @GetMapping("/{name}")
    public CurrencyEntity getCurrency(@PathVariable String name) {
        return currencyService.getCurrency(name);
    }

    @PostMapping()
    public CurrencyEntity createCurrency(@RequestBody CurrencyEntity request) {
        return currencyService.createCurrency(request);
    }

    @PutMapping("/{name}")
    public CurrencyEntity updateCurrency(@PathVariable String name,
                                         @RequestBody CurrencyEntity request) {
        return currencyService.updateCurrency(name, request);
    }

    @DeleteMapping("/{name}")
    public Boolean deleteCurrency(@PathVariable String name) {
        return currencyService.deleteCurrency(name);
    }

    @GetMapping("/getCoinDesk")
    public CoinDesk getCoinDesk() {
        return callCoinDesk();
    }

    @GetMapping("/updateCoinDesk")
    public List<CurrencyEntity> updateCoinDesk() {
        List<CurrencyEntity> currencyEntityList = new ArrayList<>();

        CoinDesk coinDesk = callCoinDesk();

        Bpi bpi = coinDesk.getBpi();
        if (bpi.getUSD() != null) {
            Code code = bpi.getUSD();
            currencyEntityList.add(updateCurrency(code));
        }

        if (bpi.getGBP() != null) {
            Code code = bpi.getGBP();
            currencyEntityList.add(updateCurrency(code));
        }

        if (bpi.getEUR() != null) {
            Code code = bpi.getEUR();
            currencyEntityList.add(updateCurrency(code));
        }

        return currencyEntityList;
    }

    public CoinDesk callCoinDesk() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new JavaScriptMessageConverter());

        CoinDesk coinDesk = restTemplate.getForObject(
                "https://api.coindesk.com/v1/bpi/currentprice.json",
                CoinDesk.class);

        return coinDesk;
    }

    public CurrencyEntity updateCurrency(Code code) {
        CurrencyEntity currencyEntity = new CurrencyEntity();
        String s = code.getCode();
        CurrencyEntity oldCurrencyEntity = currencyScan.getCurrencyEntity(s);
        currencyEntity.setName(s);
        currencyEntity.setNameZh(oldCurrencyEntity.getNameZh());
        currencyEntity.setRate(code.getRate());
        return currencyService.updateCurrency(s, currencyEntity);
    }
}
