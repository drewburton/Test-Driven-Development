package com.drewburton;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TestTemplateParse {

    private List<Segment> parse(String templateText) {
        return new TemplateParse().parse(templateText);
    }

    private void assertSegments(List<?> actual, Object... expected) {
        assertEquals("Number of segments doesn't match.", expected.length, actual.size());
        assertEquals(Arrays.asList(expected), actual);
    }

    @Test
    public void parsingTemplateIntoSegmentObjects() throws Exception {
        TemplateParse p = new TemplateParse();
        List<Segment> segments = p.parse("a ${b} c ${d}");
        assertSegments(segments,
                new PlainText("a "), new Variable("b"),
                new PlainText(" c "), new Variable("d"));
    }

    @Test
    public void emptyTemplateRendersAsEmptyString() throws Exception {
        List<Segment> segments = parse("");
        assertSegments(segments, new PlainText(""));
    }

    @Test
    public void templateWithOnlyPlainText() throws Exception {
        List<Segment> segments = parse("plain text only");
        assertSegments(segments, new PlainText("plain text only"));
    }

    @Test
    public void parsingMultipleVariables() throws Exception {
        List<Segment> segments = parse("${a}:${b}:${c}");
        assertSegments(segments, new Variable("a"), new PlainText(":"),
                new Variable("b"), new PlainText(":"), new Variable("c"));
    }
}
