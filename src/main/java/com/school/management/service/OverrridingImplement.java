package com.school.management.service;

public class OverrridingImplement implements Overriding{
    @Override
    public Integer show(Integer num) {
        try{

        }catch(Exception x){
            x.printStackTrace();
        }
        return 0;
    }
}
