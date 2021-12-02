package com.nuri.test.domain.service;

import com.nuri.test.domain.entity.HelloWorld;

import java.util.List;

public interface HelloWorldService {
    public List<HelloWorld> findByCondition(HelloWorld helloWorld);

}
