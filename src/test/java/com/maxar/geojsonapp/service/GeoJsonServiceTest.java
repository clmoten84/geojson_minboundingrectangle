package com.maxar.geojsonapp.service;

import org.geojson.LngLatAlt;
import org.geojson.Polygon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GeoJsonServiceTest {

    @Autowired
    private IGeoJsonService geoJsonService;

    @DisplayName("Compute Minimum Bounding Rectangle - Success Case")
    @Test
    public void testComputeMinimumBoundingRectangle() {
        // Execute method under test
        Polygon inputPoly = constructTestPolygon();
        Polygon minBoundRec = geoJsonService.computeMinimumBoundingRectangle(inputPoly);
        double expectedMinLong = -105.12923684;
        double expectedMaxLong = -104.8316255;
        double expectedMinLat = 39.67717782;
        double expectedMaxLat = 39.82991355;

        // Assertions
        assertNotNull(minBoundRec);

        List<List<LngLatAlt>> minBoundRecCoordList = minBoundRec.getCoordinates();
        assertNotNull(minBoundRecCoordList);
        assertEquals(1, minBoundRecCoordList.size());

        List<LngLatAlt> minBoundRecCoords = minBoundRecCoordList.get(0);
        assertEquals(5, minBoundRecCoords.size());

        // First vertex in minBoundRec is (minLong, minLat)
        assertEquals(expectedMinLong, minBoundRecCoords.get(0).getLongitude());
        assertEquals(expectedMinLat, minBoundRecCoords.get(0).getLatitude());

        // Second vertex in minBoudRec is (minLong, maxLat)
        assertEquals(expectedMinLong, minBoundRecCoords.get(1).getLongitude());
        assertEquals(expectedMaxLat, minBoundRecCoords.get(1).getLatitude());

        // Third vertex in minBoundRec is (maxLong, maxLat)
        assertEquals(expectedMaxLong, minBoundRecCoords.get(2).getLongitude());
        assertEquals(expectedMaxLat, minBoundRecCoords.get(2).getLatitude());

        // Fourth vertex in minBoundRec is (maxLong, minLat)
        assertEquals(expectedMaxLong, minBoundRecCoords.get(3).getLongitude());
        assertEquals(expectedMinLat, minBoundRecCoords.get(3).getLatitude());

        // Fifth vertex is same as first vertext to close polygon (minLong, minLat)
        assertEquals(expectedMinLong, minBoundRecCoords.get(4).getLongitude());
        assertEquals(expectedMinLat, minBoundRecCoords.get(4).getLatitude());
    }

    @DisplayName("Compute Minimum Bounding Rectangle - Input Polygon NULL")
    @Test
    public void testComputeMinimumBoundingRectangle_whenInputPolygonNull() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            Polygon minBoundRec = geoJsonService.computeMinimumBoundingRectangle(null);
        });

        assertEquals("Polygon arg was found to be null.", exception.getMessage());
    }

    @DisplayName("Compute Minimum Bounding Rectangle - Input Polygon has no list of vertices")
    @Test
    public void testComputeMinimumBoundingRectangle_whenInputPolygonHasNoVerticesList() {
        Polygon polygon = new Polygon();
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            Polygon minBoundRec = geoJsonService.computeMinimumBoundingRectangle(polygon);
        });

        assertEquals("Input polygon geometry is not a valid single polygon geometry.", exception.getMessage());
    }

    @DisplayName("Compute Minimum Bounding Rectangle - Input Polygon has no vertices")
    @Test
    public void testComputeMinimumBoundingRectangle_whenInputPolygonHasNoVertices() {
        Polygon polygon = new Polygon();
        polygon.setCoordinates(new ArrayList<>());
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            Polygon minBoundRec = geoJsonService.computeMinimumBoundingRectangle(polygon);
        });

        assertEquals("Input polygon geometry is not a valid single polygon geometry.", exception.getMessage());
    }

    /**
     * Constructs a sample Polygon instance to use for testing
     * @return Polygon instance
     */
    private Polygon constructTestPolygon() {
        // List of lat/long coordinates to use
        List<LngLatAlt> coordinates = new ArrayList<>();
        coordinates.add(new LngLatAlt(-105.05678173, 39.67717782));
        coordinates.add(new LngLatAlt(-105.12923684, 39.71973826));
        coordinates.add(new LngLatAlt(-105.11209692, 39.78921714));
        coordinates.add(new LngLatAlt(-105.04509542, 39.82991355));
        coordinates.add(new LngLatAlt(-104.94147681, 39.75029364));
        coordinates.add(new LngLatAlt(-104.87603348, 39.80896988));
        coordinates.add(new LngLatAlt(-104.8316255, 39.74849664));
        coordinates.add(new LngLatAlt(-104.93446502, 39.69396535));
        coordinates.add(new LngLatAlt(-105.05678173, 39.67717782));

        return new Polygon(coordinates);
    }
}
