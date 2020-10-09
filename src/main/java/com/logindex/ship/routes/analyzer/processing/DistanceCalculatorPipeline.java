package com.logindex.ship.routes.analyzer.processing;

import com.logindex.ship.routes.analyzer.math.Distance;
import com.logindex.ship.routes.analyzer.model.ShipRoute;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * This step calculates distance for each routes based on geo points
 *
 * @author Rafał Bajek
 */
class DistanceCalculatorPipeline implements Pipeline<List<ShipRoute>, List<SharedProcessingTypes.LegDistance>> {

    private final Distance distance;

    DistanceCalculatorPipeline(Distance distance) {
        this.distance = distance;
    }

    @Override
    public List<SharedProcessingTypes.LegDistance> process(List<ShipRoute> shipRoutes) {
        List<SharedProcessingTypes.LegDistance> legDistances = new ArrayList<>();

        shipRoutes.forEach(shipRoute -> {
            int start = 0, end = 1;
            double sum = 0.0;
            while(end < shipRoute.getPoints().size()) {
                sum += distance.calculate(shipRoute.getPoints().get(start++), shipRoute.getPoints().get(end++));
            }
            legDistances.add(new SharedProcessingTypes.LegDistance(shipRoute, new BigDecimal(Double.toString(sum)).setScale(2, RoundingMode.HALF_UP).doubleValue()));
        });
        return legDistances;
    }
}
