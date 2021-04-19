package com.drewburton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateParse {
    public TemplateParse() {

    }

    public List<Segment> parse(String template) {
        List<Segment> segments = new ArrayList<>();
        int index = collectSegments(segments, template);
        addTail(segments, template, index);
        addEmptyStringIfTemplateWasEmpty(segments);
        return segments;
    }

    private int collectSegments(List<Segment> segs, String src) {
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
    private void addTail(List<Segment> segs, String template, int index) {
        if (index < template.length()) {
            segs.add(new PlainText(template.substring(index)));
        }
    }

    // adds a found variables to segs using the matcher
    private void addVariable(List<Segment> segs, String src, Matcher m) {
        segs.add(new Variable(src.substring(m.start() + 2, m.end() - 1)));
    }

    // adds the plain text from index until the next variable found
    private void addPrecedingPlainText(List<Segment> segs, String src, Matcher m, int index) {
        if (index != m.start()) {
            segs.add(new PlainText(src.substring(index, m.start())));
        }
    }

    private void addEmptyStringIfTemplateWasEmpty(List<Segment> segs) {
        if (segs.isEmpty()) {
            segs.add(new PlainText(""));
        }
    }
}

