package com.example.caty_interview.Controller;

import com.example.caty_interview.Entity.CoinDesk;
import com.example.caty_interview.Entity.CurrencyEntity;
import com.example.caty_interview.Service.CurrencyService;
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
        CoinDesk coinDesk = callCoinDesk();

        List<CurrencyEntity> currencyEntityList = new ArrayList<>();

        CurrencyEntity currencyUSD = new CurrencyEntity();
        if (currencyUSD != null) {
            currencyUSD.setName(coinDesk.getBpi().getUSD().getCode());
            currencyUSD.setRate(coinDesk.getBpi().getUSD().getRate());
            currencyEntityList.add(currencyService.updateCurrency(currencyUSD.getName(), currencyUSD));
        }
        CurrencyEntity currencyGBP = new CurrencyEntity();
        if (currencyGBP != null) {
            currencyGBP.setName(coinDesk.getBpi().getGBP().getCode());
            currencyGBP.setRate(coinDesk.getBpi().getGBP().getRate());
            currencyEntityList.add(currencyService.updateCurrency(currencyGBP.getName(), currencyGBP));
        }

        CurrencyEntity currencyEUR = new CurrencyEntity();
        if (currencyEUR != null) {
            currencyEUR.setName(coinDesk.getBpi().getEUR().getCode());
            currencyEUR.setRate(coinDesk.getBpi().getEUR().getRate());
            currencyEntityList.add(currencyService.updateCurrency(currencyEUR.getName(), currencyEUR));
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
}
