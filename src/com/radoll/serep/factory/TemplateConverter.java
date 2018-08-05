package com.radoll.serep.factory;

import com.radoll.serep.models.DockModel;

import java.util.List;
import java.util.Set;

public class TemplateConverter {


    public String convert(final List<DockModel> docks, final String template) {

        String result = "";

        int x1 = 0;
        int x2 = 1;

        System.out.println("Docks: " + docks.size());


        for (int x = 1; docks.size() - 1 >= x; x++) {


            final DockModel dock1 = docks.get(x1);
            final DockModel dock2 = docks.get(x2);

            final String firstRound = template.replaceAll("x1", dock1.getValue());
            String finalRound = firstRound.replaceAll("x2", dock2.getValue());

            final Outlier outlier = new Outlier();
            final Set<String> outliers = outlier.find(template);

            if (!outliers.isEmpty()) {
                for (String currentOutlier : outliers) {
                    for (DockModel dockModel : docks) {

                        String id = dockModel.getId();

                        id = id.replace("dock_0", "x");


                        if (id.equals(currentOutlier)) {
                            finalRound = finalRound.replaceAll(currentOutlier, dockModel.getValue());

                        }
                    }
                }
            }

            result = result.concat(finalRound).concat("\n");

            x1 = x1 + 1;
            x2 = x2 + 1;
        }

        return result;
    }
}
