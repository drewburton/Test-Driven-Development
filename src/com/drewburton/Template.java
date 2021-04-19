package com.drewburton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Template {
    private final Map<String, String> variables;
    private final String templateText;

    public Template(String templateText) {
        variables = new HashMap<>();
        this.templateText = templateText;
    }

    public void set(String string, String value) {
        variables.put(string, value);
    }

    public String evaluate() {
        TemplateParse parser = new TemplateParse();
        List<Segment> segments = parser.parse(templateText);
        return concatenate(segments);
    }

    private String concatenate(List<Segment> segments) {
        StringBuilder result = new StringBuilder();
        for (Segment segment : segments) {
            result.append(segment.evaluate(variables));
        }
        return result.toString();
    }
}
