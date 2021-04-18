package com.drewburton;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class RegexLearningTest {
    @Test
    public void testHowGroupCountWorks() throws Exception {
        String haystack = "The needle shop sells needles";
        String regex = "needle";
        Matcher matcher = Pattern.compile(regex).matcher(haystack);

        assertTrue(matcher.find());
        assertEquals("Wrong start index of first match", 4, matcher.start());
        assertEquals("Wrong end index of first match", 10, matcher.end());

        assertTrue(matcher.find());
        assertEquals("Wrong start index of second match", 22, matcher.start());
        assertEquals("Wrong end index of second match", 28, matcher.end());

        assertFalse("Should not have any more matches", matcher.find());
    }
}
