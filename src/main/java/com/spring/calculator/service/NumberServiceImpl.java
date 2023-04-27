package com.spring.calculator.service;

import com.spring.calculator.model.Number;
import com.spring.calculator.model.NumberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

// Service- servisų sloksnis "verlo" logikai
// po serviso sluoksniu kreipiamės į DB
@Service
public class NumberServiceImpl implements NumberService {

    // @Autowired naud automat priklausomybių injekcijai (IOC -iversion of control)
    // kad panaudoti @Autowired anotaciją reikia pirmaiausia turėti apsirašius @Been @Configuration klasė
    @Autowired

/* @Qualifier anotacija kartu su @ autowired patikslina su kuriuo konkrečiai Been susieti priklausomybę.
 Jeigu @Configuration klasėje yra daugiau nei 1 Been, @Quolifier anotacija yra privaloma, kitų atvėju metama klaida:
  'Consider marking one of the beans as @Primary, updating the consumer to accept multiple beans,
  or using @Qualifier to identify the bean that should be consumed' */

    @Qualifier("NumberDAO")
    private NumberDAO numberDAO;

    @Override
    public void insert(Number number) {
    numberDAO.insertEntity(number);
    }

    @Override
    public Number getById(int id) {
        return numberDAO.findEntityById(id);
    }

    @Override
    public List<Number> getAll() {
        return numberDAO.findEntities();
    }

    @Override
    public void update(Number number) {
    numberDAO.updateEntity(number);
    }

    @Override
    public void delete(int id) {
    numberDAO.removeEntityById(id);
    }
}
