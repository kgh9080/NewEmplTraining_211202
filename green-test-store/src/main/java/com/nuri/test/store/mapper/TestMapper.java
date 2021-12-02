package com.nuri.test.store.mapper;

import com.nuri.test.store.jpo.TestJpo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {
    List<TestJpo> findByCondition(TestJpo testJpo);
}
