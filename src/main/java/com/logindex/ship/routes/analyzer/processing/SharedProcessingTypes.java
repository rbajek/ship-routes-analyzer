package com.logindex.ship.routes.analyzer.processing;

import com.logindex.ship.routes.analyzer.model.ShipRoute;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Rafał Bajek
 */
class SharedProcessingTypes {

    @RequiredArgsConstructor
    @Getter
    static class LegDistance {
        private final ShipRoute shipRoute;
        private final double sumDistances;
    }

    @RequiredArgsConstructor
    static class PopularDistance {
        @Getter
        private final double sumDistances;
        private Map<String, ShipRoutePopularDistanceCounter> shipRouteOccurCountMap = new HashMap<>();

        @Getter
        private long totalOccurCount;

        void incrementOccurCount(ShipRoute shipRoute) {
            ShipRoutePopularDistanceCounter shipRoutePopularDistanceCounter = shipRouteOccurCountMap.get(shipRoute.getRouteId());
            if(shipRoutePopularDistanceCounter == null) {
                shipRoutePopularDistanceCounter = new ShipRoutePopularDistanceCounter(shipRoute);
            }
            shipRoutePopularDistanceCounter.incrementTotalOccurCount();
            shipRouteOccurCountMap.put(shipRoute.getRouteId(), shipRoutePopularDistanceCounter);
            totalOccurCount++;
        }

        List<ShipRoutePopularDistanceCounter> getRoutes() {
            return shipRouteOccurCountMap.values().stream().collect(Collectors.toUnmodifiableList());
        }
    }

    @RequiredArgsConstructor
    @Getter
    static class ShipRoutePopularDistanceCounter {
        private final ShipRoute shipRoute;
        private long totalOccurCount;

        void incrementTotalOccurCount() {
            totalOccurCount++;
        }
    }
}
