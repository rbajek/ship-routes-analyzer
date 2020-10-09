package com.logindex.ship.routes.analyzer.processing;

import com.logindex.ship.routes.analyzer.model.ShipRoute;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;

/**
 * This step prints the most representative route
 *
 * @author Rafał Bajek
 */
class PrintTheMostRepresentativeRoutePipeline implements Pipeline<ShipRoute, Void> {

    @Override
    public Void process(ShipRoute theMostRepresentativeRoute) {
        String pointsStr = "[["+theMostRepresentativeRoute.getPoints().get(0).getLongitude()+", "+theMostRepresentativeRoute.getPoints().get(0).getLatitude()+",...";

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("ship_id", "route_id", "from_port", "to_port", "leg_duration", "count", "points");
        at.addRule();
        at.addRow(theMostRepresentativeRoute.getShipId(), theMostRepresentativeRoute.getRouteId(), theMostRepresentativeRoute.getFromPort(), theMostRepresentativeRoute.getToPort(), theMostRepresentativeRoute.getLegDuration(), theMostRepresentativeRoute.getReadPoints(), pointsStr);
        at.addRule();

        CWC_LongestLine cwc = new CWC_LongestLine();
        at.getRenderer().setCWC(cwc);

        System.out.println("======================================================================");
        System.out.println("        The most representative route      ");
        System.out.println("======================================================================");

        System.out.println(at.render());
        return null;
    }
}
