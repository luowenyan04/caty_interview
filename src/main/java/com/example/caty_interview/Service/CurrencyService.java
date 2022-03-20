package com.example.caty_interview.Service;

import com.example.caty_interview.DTO.CurrencyDTO;
import com.example.caty_interview.Entity.CurrencyEntity;
import com.example.caty_interview.Repository.CurrencyRepository;
import com.example.caty_interview.Tool.UpdateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    public Optional<CurrencyEntity> getCurrency(String name) {
        return currencyRepository.findById(name);
    }

    public CurrencyEntity createCurrency(CurrencyDTO request) {
        CurrencyEntity currencyEntity = new CurrencyEntity();
        currencyEntity.setName(request.getName());
        currencyEntity.setNameZh(request.getNameZh());
        currencyEntity.setRate(request.getRate());
        return currencyRepository.save(currencyEntity);
    }

    public CurrencyEntity updateCurrency(String name, CurrencyDTO request) throws Exception {
        Optional<CurrencyEntity> currencyOp = getCurrency(name);
        if (currencyOp.isPresent()) {
            CurrencyEntity currencyEntity = currencyOp.get();
            UpdateTool.copyNullProperties(currencyEntity, request);
            currencyEntity.setNameZh(request.getNameZh());
            currencyEntity.setRate(request.getRate());
            return currencyRepository.save(currencyEntity);
        } else {
            throw new Exception("No data found.");
        }
    }

    public Boolean deleteCurrency(String name) {
        currencyRepository.deleteById(name);
        return true;
    }

}
