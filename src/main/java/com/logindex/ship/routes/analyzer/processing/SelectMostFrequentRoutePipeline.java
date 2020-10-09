package com.logindex.ship.routes.analyzer.processing;

import com.logindex.ship.routes.analyzer.model.ShipRoute;

/**
 * This step selects the most frequent route for the given distance
 *
 * @author Rafał Bajek
 */
class SelectMostFrequentRoutePipeline implements Pipeline<SharedProcessingTypes.PopularDistance, ShipRoute> {

    @Override
    public ShipRoute process(SharedProcessingTypes.PopularDistance popularDistance) {
        return popularDistance.getRoutes()
                .stream()
                .sorted((l1, l2) -> Long.compare(l1.getTotalOccurCount(), l2.getTotalOccurCount()) * (-1))
                .findFirst()
                .get()
                .getShipRoute();
    }
}
