package com.drewburton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateParse {
    public TemplateParse() {

    }

    public List<Segment> parseSegments(String template) {
        List<Segment> segments = new ArrayList<>();
        List<String> strings = parse(template);
        for (String s : strings) {
            if (isVariable(s)) {
                String name = s.substring(2, s.length() - 1);
                segments.add(new Variable(name));
            } else {
                segments.add(new PlainText(s));
            }
        }
        return segments;
    }

    public List<String> parse(String text) {
        List<String> segments = new ArrayList<>();
        int index = collectSegments(segments, text);
        // add remaining text after last variable and check if it was empty
        addTail(segments, text, index);
        addEmptyStringIfTemplateWasEmpty(segments);
        return segments;
    }

    private int collectSegments(List<String> segs, String src) {
        // we can't capture closing brackets or it will connect the first and last varible into one segment
        Pattern pattern = Pattern.compile("\\$\\{[^}]*\\}");
        Matcher matcher = pattern.matcher(src);
        int index = 0;
        while (matcher.find()) {
            // add the text before each variable, then the variable
            // then set the index pointer after it
            addPrecedingPlainText(segs, src, matcher, index);
            addVariable(segs, src, matcher);
            index = matcher.end();
        }
        return index;
    }

    // adds from index to the end of template to segs
    private void addTail(List<String> segs, String template, int index) {
        if (index < template.length()) {
            segs.add(template.substring(index));
        }
    }

    // adds a found variables to segs using the matcher
    private void addVariable(List<String> segs, String src, Matcher m) {
        segs.add(src.substring(m.start(), m.end()));
    }

    // adds the plain text from index until the next variable found
    private void addPrecedingPlainText(List<String> segs, String src, Matcher m, int index) {
        if (index != m.start()) {
            segs.add(src.substring(index, m.start()));
        }
    }

    private void addEmptyStringIfTemplateWasEmpty(List<String> segs) {
        if (segs.isEmpty()) {
            segs.add("");
        }
    }

    private boolean isVariable(String segment) {
        return segment.startsWith("$") && segment.endsWith("}");
    }
}

