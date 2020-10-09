package com.logindex.ship.routes.analyzer.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * Ship's route
 *
 * @author Rafał Bajek
 */
@ToString
@Builder
@Getter
public class ShipRoute {
    private final String shipId;
    private final String routeId;
    private final String fromPort;
    private final String toPort;
    private final long legDuration;
    private final int readPoints;
    private final List<Point> points;
}