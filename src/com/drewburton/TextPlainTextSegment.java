package com.drewburton;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TextPlainTextSegment {
    @Test
    public void plainTextEvaluatesAsIs() throws Exception {
        Map<String, String> variables = new HashMap<>();
        String text = "abc def";
        assertEquals(text, new PlainText(text).evaluate(variables));
    }
}
