package com.nuri.test.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelloWorld implements Serializable {

	private static final long serialVersionUID = -8013486474781575677L;

	private int testId;

	private String text;


}
