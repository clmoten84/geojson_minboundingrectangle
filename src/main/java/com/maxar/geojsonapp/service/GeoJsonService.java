package com.maxar.geojsonapp.service;

import org.geojson.LngLatAlt;
import org.geojson.Polygon;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeoJsonService implements IGeoJsonService {
    /**
     * Computes the minimum bounding rectangle polygon for the arg Polygon
     * MBR is computed by finding min(x), min(y), max(x), max(y) vertices of the given Polygon
     *
     * Example:
     *
     *     .(min-x, max-y)               .(max-x, max-y)
     *
     *     .(min-x, min-y)               .(max-x, min-y)
     *
     * @return Polygon representing minimum bounding rectangle of arg Polygon
     * @throws IllegalArgumentException if arg Polygon is null or not valid
     */
    @Override
    public Polygon computeMinimumBoundingRectangle(Polygon polygon) {
        if (polygon == null) {
            throw new IllegalArgumentException("Polygon arg was found to be null.");
        }

        // Get list of polygon coordinates
        List<List<LngLatAlt>> inputPolygonCoordsList = polygon.getCoordinates();

        // This method only computes MBR for a single polygon geometry. This method does not support
        // multi-polygon geometries. So the size of the outer list of coords should be only 1.
        // If it is not, an IllegalArgumentException is thrown.
        if (inputPolygonCoordsList.size() != 1 || inputPolygonCoordsList.get(0).size() == 0) {
            throw new IllegalArgumentException("Input polygon geometry is not a valid single polygon geometry.");
        }

        // Compute min and max x and y coordinates from list of lat/longs
        List<Double> allLongitudes = inputPolygonCoordsList.get(0).stream().map(LngLatAlt::getLongitude).collect(Collectors.toList());
        List<Double> allLatitudes = inputPolygonCoordsList.get(0).stream().map(LngLatAlt::getLatitude).collect(Collectors.toList());
        double minLong = Collections.min(allLongitudes);
        double minLat = Collections.min(allLatitudes);
        double maxLong = Collections.max(allLongitudes);
        double maxLat = Collections.max(allLatitudes);

        return new Polygon(new LngLatAlt(minLong, minLat),
                            new LngLatAlt(minLong, maxLat),
                            new LngLatAlt(maxLong, maxLat),
                            new LngLatAlt(maxLong, minLat),
                            new LngLatAlt(minLong, minLat));
    }
}
