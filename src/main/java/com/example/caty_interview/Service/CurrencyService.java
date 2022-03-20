package com.example.caty_interview.Service;

import com.example.caty_interview.Entity.CurrencyEntity;
import com.example.caty_interview.Repository.CurrencyRepository;
import com.example.caty_interview.Tool.UpdateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    public CurrencyEntity getCurrency(String name) {
        return currencyRepository.findById(name)
                .orElse(null);
    }

    public CurrencyEntity createCurrency(CurrencyEntity request) {
        currencyRepository.save(request);
        return request;
    }

    public CurrencyEntity updateCurrency(String name, CurrencyEntity request) {
        CurrencyEntity currency = getCurrency(name);
        if (currency != null) {
            UpdateTool.copyNullProperties(currency, request);
            currency.setNameZh(request.getNameZh());
            currency.setRate(request.getRate());
            return currencyRepository.save(currency);
        } else {
            return null;
        }
    }

    public Boolean deleteCurrency(String name) {
        currencyRepository.deleteById(name);
        return true;
    }

}
