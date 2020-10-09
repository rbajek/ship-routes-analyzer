package com.logindex.ship.routes.analyzer.math;

import com.logindex.ship.routes.analyzer.model.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Rafał Bajek
 */
class HaversizeDistanceTest {

    private HaversizeDistance haversizeDistance = new HaversizeDistance();

    @Test
    void calculate() {
        // given
        Point point1 = new Point(8.489074, 53.615707, 14.0);
        Point point2 = new Point(9.909705, 53.53625, 14.0);

        //when
        double distanceInKm = haversizeDistance.calculate(point1, point2);

        //then
        assertEquals(94, Math.round(distanceInKm));
    }
}