package com.nuri.test.store.jpo;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Alias("testJpo")
public class TestJpo implements Serializable {

	private static final long serialVersionUID = -5318878161053888886L;

	private int testId;

	private String text;


}
