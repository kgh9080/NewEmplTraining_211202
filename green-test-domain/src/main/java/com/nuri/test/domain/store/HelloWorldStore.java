package com.nuri.test.domain.store;

import com.nuri.test.domain.entity.HelloWorld;

import java.util.List;

public interface HelloWorldStore {
    public List<HelloWorld> findByCondition(HelloWorld helloWorld);
}
