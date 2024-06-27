package com.pcwk.ehr.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

//스프링 컨텍스트 설정 파일을 로드
@ContextConfiguration(locations = { "classpath:testApplicationContext.xml" })
//JUnit 5와 스프링 통합
@SpringJUnitConfig
class CalculatorTest2 {

    @Autowired
    private Calculator calculator; // 스프링 빈 주입

    @Test
    public void testAdd() {
        assertEquals(5, calculator.add(2, 3));
    }

    @Test
    public void testSubtract() {
        assertEquals(1, calculator.subtract(3, 2));
    }

}
