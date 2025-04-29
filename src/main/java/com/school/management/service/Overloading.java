package com.school.management.service;

import jakarta.persistence.criteria.CriteriaBuilder;

public class Overloading {
    public Integer  show(Integer num){
        return num;
    }

    public Integer show(Integer num,Integer num1){
        return num+num1;
    }
}
