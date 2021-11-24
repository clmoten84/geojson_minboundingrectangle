package com.maxar.geojsonapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.geojson.LngLatAlt;
import org.geojson.Polygon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GeoJsonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Tests success case of computeMinimumBoundingRectangle method on GeoJsonController
     */
    @DisplayName("POST: Compute Minimum Bounding Rectangle")
    @Test
    public void testComputeMininumBoundingRectangle() {
        try {
            Polygon inputPolygon = constructTestPolygon();
            String inputPolygonJson = objectMapper.writeValueAsString(inputPolygon);

            MvcResult testResult = this.mockMvc.perform(post("/geojson/mbr")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(inputPolygonJson))
                    .andExpect(status().isOk())
                    .andReturn();
            assertEquals(MediaType.APPLICATION_JSON_VALUE, testResult.getResponse().getContentType());

            // Get response polygon from result and validate
            double expectedMinLong = -105.12923684;
            double expectedMaxLong = -104.8316255;
            double expectedMinLat = 39.67717782;
            double expectedMaxLat = 39.82991355;
            Polygon resultPolygon = this.getResponsePolygon(testResult);
            assertNotNull(resultPolygon);

            List<List<LngLatAlt>> minBoundRecCoordList = resultPolygon.getCoordinates();
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
        catch (Exception ex) {
            fail("An unexpected error occurred during test...");
        }
    }

    /**
     * Tests fail case of computeMinimumBoundingRectangle method on GeoJsonController
     */
    @DisplayName("POST: Compute Minimum Bounding Rectangle - when input polygon is invalid")
    @Test
    public void testComputeMininumBoundingRectangle_whenInputPolygonIsInvalid() {
        try {
            String inputPolyJson = "";
            this.mockMvc.perform(post("/geojson/mbr")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(inputPolyJson))
                    .andExpect(status().isBadRequest())
                    .andReturn();
        }
        catch (Exception ex) {
            fail("An unexpected error occurred during test...");
        }
    }

    /**
     * Converts the response contained inside arg result into the expected Polygon instance type
     * @param result MvcResult
     * @return Polygon instance
     */
    private Polygon getResponsePolygon(MvcResult result) throws Exception {
        return objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), Polygon.class);
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
