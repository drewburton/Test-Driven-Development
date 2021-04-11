package com.drewburton;

import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class TemplateTestPerformance {
    private Template template;

    //Omitted setup
    @Before
    public void setUp() {
        StringBuilder generatedString = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            generatedString.append("${").append(i).append("} ");
        }
        template = new Template(generatedString.toString());
        for (int i = 0; i < 20; i++) {
            template.set(i + "", "lotsOfLettersToPutIn");
        }
    }

    @Test
    public void templateWith100WordsAnd20Variables() throws Exception {
        long expected = 200L;
        long time = System.currentTimeMillis();
        template.evaluate();
        time = System.currentTimeMillis() - time;
        assertTrue("Rendering the template took " + time + "ms while the target was " + expected + "ms", time <= expected);
    }
}
