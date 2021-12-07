package com.nuri.test;

import com.nuri.test.domain.entity.HelloWorld;
import com.nuri.test.domain.store.HelloWorldStore;
import com.nuri.test.store.jpo.TestJpo;
import com.nuri.test.store.mapper.TestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestMapperStore implements HelloWorldStore {

    private final TestMapper testMapper;

    @Override
    public List<HelloWorld> findByCondition(HelloWorld helloWorld){
        TestJpo testJpo = new TestJpo();
        testJpo.setTestId(helloWorld.getTestId());
        List<TestJpo> list1 = testMapper.findByCondition(testJpo);
        List<HelloWorld> list2 = new ArrayList<>();
        HelloWorld helloWorld1 = new HelloWorld();


        for (int i = 0; i < list1.size(); i++) {
            helloWorld1.setTestId(list1.get(i).getTestId());
            helloWorld1.setText(list1.get(i).getText());
            list2.add(i, helloWorld1);
        }
        return list2;
    }
}
