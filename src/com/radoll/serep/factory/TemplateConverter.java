package com.radoll.serep.factory;

import com.radoll.serep.models.DockModel;

import java.util.List;

public class TemplateConverter {


    public String convert(final List<DockModel> docks, final String template) {

        String result = "";

        int x1 = 0;
        int x2 = 1;

        System.out.println("Docks: " + docks.size());


        for (int x = 1; docks.size()-1 >= x; x++) {


            String copy = template;

            System.out.println("dock 1: " + x1);
            System.out.println("dock 2: " + x2);
            System.out.println("  ");

            final DockModel dock1 = docks.get(x1);
            final DockModel dock2 = docks.get(x2);

            final String firstRound = copy.replaceAll("x1", dock1.getValue());
            final String finalRound = firstRound.replaceAll("x2", dock2.getValue());

            result = result.concat(finalRound).concat("\n");

            x1 = x1 + 1;
            x2 = x2 + 1;
        }

        return result;
    }
}
