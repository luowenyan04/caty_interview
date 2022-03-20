package com.example.caty_interview.Repository;

import com.example.caty_interview.Entity.CurrencyEntity;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepository extends CrudRepository<CurrencyEntity, String> {
}
