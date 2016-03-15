package ru.goryachev.calculator;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import ru.goryachev.calculator.logic.RPNCalculator;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RPNTest extends Assert {
    private final List<String> testOperands = new ArrayList<>();
    private double testResult = -8d;

    @Before
    public void setUpTestData(){
        testOperands.add("(");
        testOperands.add("2");
        testOperands.add("+");
        testOperands.add("2");
        testOperands.add(")");
        testOperands.add("*");
        testOperands.add("-2");
    }


    @Test
    public void testExpressions() throws ParseException {
        RPNCalculator rpnCalculator = new RPNCalculator();
        rpnCalculator.setOperands(testOperands);
        rpnCalculator.parse();
        assertEquals(rpnCalculator.calculate(), testResult, 0);
    }

}
