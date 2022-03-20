package com.example.caty_interview.Controller;

import com.example.caty_interview.DTO.CurrencyDTO;
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
import java.util.Currency;
import java.util.List;

@RestController
@RequestMapping(value = "/caty", produces = MediaType.APPLICATION_JSON_VALUE)
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CurrencyScan currencyScan;

    @GetMapping("/{name}")
    public CurrencyEntity getCurrency(@PathVariable String name) {
        return currencyService.getCurrency(name).get();
    }

    @PostMapping
    public CurrencyEntity createCurrency(@RequestBody CurrencyEntity request) {
        return currencyService.createCurrency(request);
    }

    @PutMapping("/{name}")
    public CurrencyEntity updateCurrency(@PathVariable String name,
                                         @RequestBody CurrencyDTO request) throws Exception {
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
    public List<CurrencyEntity> updateCoinDesk() throws Exception {
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

    public CurrencyEntity updateCurrency(Code code) throws Exception {
        CurrencyDTO currencyDTO = new CurrencyDTO();
        String s = code.getCode();
        CurrencyEntity oldCurrencyEntity = currencyScan.getCurrencyEntity(s);
        currencyDTO.setName(s);
        currencyDTO.setNameZh(oldCurrencyEntity.getNameZh());
        currencyDTO.setRate(code.getRate());
        return currencyService.updateCurrency(s, currencyDTO);
    }
}
