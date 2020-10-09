package com.logindex.ship.routes.analyzer.processing;

import com.logindex.ship.routes.analyzer.model.Point;
import com.logindex.ship.routes.analyzer.model.ShipRoute;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Rafał Bajek
 */
class ReadDataFromCVSFilePipeline implements Pipeline<String, List<ShipRoute>> {

    private static final Pattern POINTS_PATTERN = Pattern.compile("\\[(.*?)\\]");
    private static final String POINTS_DELIMITER = ",";

    private static Function<String, Double> CONVERTER = (s) -> {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return 0.0;
        }
    };

    @Override
    public List<ShipRoute> process(String cvsFilePath) {
        List<ShipRoute> shipRoutes = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(cvsFilePath))) {
            reader.skip(1);
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                List<Point> points = new ArrayList<>();
                Matcher matcher = POINTS_PATTERN.matcher(nextLine[7].substring(1).substring(0, nextLine[7].length() - 2));
                while(matcher.find()) {
                    String[] array = matcher.group(1).split(POINTS_DELIMITER);
                    points.add(new Point(CONVERTER.apply(array[0]), CONVERTER.apply(array[1]), CONVERTER.apply(array[3])));
                }

                shipRoutes.add(
                        ShipRoute.builder()
                                .shipId(nextLine[0])
                                .routeId(nextLine[1] + "_"+ nextLine[2])
                                .fromPort(nextLine[3])
                                .toPort(nextLine[4])
                                .legDuration(Long.parseLong(nextLine[5]))
                                .readPoints(Integer.parseInt(nextLine[6]))
                                .points(points)
                                .build()
                );
            }
            return shipRoutes;
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e); // a dedicated (unchecked) exception will be better, but this is only show case
        }
    }
}
