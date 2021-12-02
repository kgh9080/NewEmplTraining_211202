package com.nuri.test.logic;

import com.nuri.test.domain.entity.HelloWorld;
import com.nuri.test.domain.service.HelloWorldService;
import com.nuri.test.domain.store.HelloWorldStore;
import com.nuri.test.store.jpo.TestJpo;
import com.nuri.test.store.mapper.TestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HelloWorldLogic implements HelloWorldService {

    private final HelloWorldStore helloWorldStore;

    @Override
    public List<HelloWorld> findByCondition(HelloWorld helloWorld){
        return helloWorldStore.findByCondition(helloWorld);
    }
}
