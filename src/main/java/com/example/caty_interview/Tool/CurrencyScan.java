package com.example.caty_interview.Tool;

import com.example.caty_interview.Entity.CurrencyEntity;
import com.example.caty_interview.Repository.CurrencyRepository;
import com.example.caty_interview.Service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CurrencyScan {

    @Autowired
    private CurrencyRepository currencyRepository;

    private Map<String, CurrencyEntity> currencyEntityMap = new HashMap<>();

    public void initCurrencyMap() {
        final List<CurrencyEntity> currencyEntityList = currencyRepository.findAll();

        for (final CurrencyEntity currencyEntity : currencyEntityList) {
            currencyEntityMap.put(currencyEntity.getName(), currencyEntity);
        }
    }

    public CurrencyEntity getCurrencyEntity(String name) {
        initCurrencyMap();
        final CurrencyEntity currencyEntity = currencyEntityMap.get(name);
        if (currencyEntity == null) {
            throw new RuntimeException("查無資料");
        }
        return currencyEntity;
    }

}
