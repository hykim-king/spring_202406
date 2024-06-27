package com.pcwk.ehr.test;

import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 테스트 인스턴스를 클래스당 한 번만 생성하도록 설정
public class LifeCycleTest {

    @BeforeAll
    public static void beforeAllTests() {
        System.out.println("Before all tests");
    }

    @AfterAll
    public static void afterAllTests() {
        System.out.println("After all tests");
    }

    @BeforeEach
    public void beforeEachTest() {
        System.out.println("Before each test");
    }

    @AfterEach
    public void afterEachTest() {
        System.out.println("After each test");
    }

    @Test
    public void test1() {
        System.out.println("Executing test 1");
        Assertions.assertTrue(true);
    }

    @Test
    public void test2() {
        System.out.println("Executing test 2");
        Assertions.assertTrue(true);
    }
}

