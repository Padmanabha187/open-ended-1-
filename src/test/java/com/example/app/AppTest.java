package com.example.app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AppTest {

    @Test
    public void testExpenseCalculation() {

        double amount1 = 1000;
        double amount2 = 500;

        double total = amount1 + amount2;

        assertEquals(1500, total, 0.01);
    }
}
