package com.spring.calculator.service;

import com.spring.calculator.model.Number;

import java.util.List;

public interface NumberService {
    //servisas yra sluoksnis ant DB DAOImpl kreipiniai į DB
    void insert(Number number);
    Number getById(int id);
    List<Number> getAll();
    void update(Number number);
    void delete(int id);



}
