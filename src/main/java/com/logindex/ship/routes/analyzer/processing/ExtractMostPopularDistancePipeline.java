package com.logindex.ship.routes.analyzer.processing;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This step extracts the most popular distance
 *
 * @author Rafał Bajek
 */
class ExtractMostPopularDistancePipeline implements Pipeline<List<SharedProcessingTypes.LegDistance>, SharedProcessingTypes.PopularDistance> {

    @Override
    public SharedProcessingTypes.PopularDistance process(List<SharedProcessingTypes.LegDistance> legDistanceList) {
        Map<Long, SharedProcessingTypes.PopularDistance> occurringMap = new HashMap<>();

        // determine the popularity of each distance for each route
        for (SharedProcessingTypes.LegDistance legDistance : legDistanceList) {
            long sumLegDuration = Math.round(legDistance.getSumDistances());
            SharedProcessingTypes.PopularDistance popularDistance = occurringMap.get(sumLegDuration);
            if(popularDistance == null) {
                popularDistance = new SharedProcessingTypes.PopularDistance(legDistance.getSumDistances());
            }
            popularDistance.incrementOccurCount(legDistance.getShipRoute());
            occurringMap.put(sumLegDuration, popularDistance);
        }

        // the most popular distance
        return occurringMap.entrySet()
                .stream()
                .sorted((o1, o2) -> Long.compare(o1.getValue().getTotalOccurCount(), o2.getValue().getTotalOccurCount()) * (-1)) // sort by desc (max will be at the first position)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o1, o2) -> o1, LinkedHashMap::new))
                .entrySet()
                .iterator()
                .next()
                .getValue(); // get the first item from the map
    }
}

/**
 * Decorator for debugging mode
 */
class DebugExtractMostPopularDistancePipelineDecorator implements Pipeline<List<SharedProcessingTypes.LegDistance>, SharedProcessingTypes.PopularDistance> {
    private final ExtractMostPopularDistancePipeline decoratedClass;

    DebugExtractMostPopularDistancePipelineDecorator(ExtractMostPopularDistancePipeline decoratedClass) {
        this.decoratedClass = decoratedClass;
    }


    @Override
    public SharedProcessingTypes.PopularDistance process(List<SharedProcessingTypes.LegDistance> request) {
        SharedProcessingTypes.PopularDistance popularDistance = decoratedClass.process(request);

        System.out.println("======================================================================");
        System.out.println("        Result of extracting the most popular distance       ");
        System.out.println("======================================================================");

        System.out.println("The most popular route has: " + popularDistance.getSumDistances() + " km");
        System.out.println("");
        System.out.println("and contains the following routes:");

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("ship_id", "route_id", "from_port", "to_port", "leg_duration", "count", "points", "how_many_times_used");
        at.addRule();
        for (SharedProcessingTypes.ShipRoutePopularDistanceCounter shipRoutePopularDistanceCounter : popularDistance.getRoutes()) {
            String pointsStr = "[["+ shipRoutePopularDistanceCounter.getShipRoute().getPoints().get(0).getLongitude()+", "+ shipRoutePopularDistanceCounter.getShipRoute().getPoints().get(0).getLatitude()+",...";

            at.addRow(
                    shipRoutePopularDistanceCounter.getShipRoute().getShipId(),
                    shipRoutePopularDistanceCounter.getShipRoute().getRouteId(),
                    shipRoutePopularDistanceCounter.getShipRoute().getFromPort(),
                    shipRoutePopularDistanceCounter.getShipRoute().getToPort(),
                    shipRoutePopularDistanceCounter.getShipRoute().getLegDuration(),
                    shipRoutePopularDistanceCounter.getShipRoute().getReadPoints(),
                    pointsStr,
                    shipRoutePopularDistanceCounter.getTotalOccurCount());
            at.addRule();
        }

        CWC_LongestLine cwc = new CWC_LongestLine();
        at.getRenderer().setCWC(cwc);

        System.out.println(at.render());


        return popularDistance;
    }
}