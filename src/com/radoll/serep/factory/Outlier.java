package com.radoll.serep.factory;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Outlier {

    public Set<String> find(String template) {

        Matcher m = Pattern.compile("x\\d").matcher(template);

        Set<String> outliers = new HashSet<>();
        while (m.find()) {
            outliers.add(m.group());
        }

        outliers.remove("x1");
        outliers.remove("x2");


        return outliers;
    }
}
