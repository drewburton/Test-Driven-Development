package com.drewburton;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestVariableSegment {
    private Map<String, String> variables;

    @Before
    public void setUp() {
        variables = new HashMap<>();
    }

    @Test
    public void variableEvaluatesToItsValue() throws Exception {
        String name = "myvar";
        String value = "myvalue";
        variables.put(name, value);
        assertEquals(value, new Variable(name).evaluate(variables));
    }

    @Test
    public void missingVariableRaisesException() throws Exception {
        String name = "myvar";
        try {
            new Variable(name).evaluate(variables);
            fail("Missing variable value should raise an exception");
        } catch(MissingValueException expected) {
            assertEquals("No value for ${" + name + "}", expected.getMessage());
        }
    }
}
