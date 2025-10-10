package com.graphhopper.util;

import com.graphhopper.util.shapes.GHPoint;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DistanceCalcEarthExtraTest {

    @Test
    void createBBox_negativeRadius_throws() {
        DistanceCalcEarth earth = new DistanceCalcEarth();
        assertThrows(IllegalArgumentException.class, () -> earth.createBBox(0.0, 0.0, -0.1));
        assertThrows(IllegalArgumentException.class, () -> earth.createBBox(45.0, -73.0, 0.0));
    }

    @Test
    void isDateLineCrossOver_edges() {
        DistanceCalcEarth earth = new DistanceCalcEarth();
        // > 180° -> true
        assertTrue(earth.isDateLineCrossOver(179.9, -179.9));
        assertTrue(earth.isDateLineCrossOver(170.0, -170.0));
        assertTrue(earth.isDateLineCrossOver(-180.0, 180.0));
        // == 180° -> false (cas limite)
        assertFalse(earth.isDateLineCrossOver(0.0, 180.0));
        assertFalse(earth.isDateLineCrossOver(0.0, -180.0));
    }

    @Test
    void projectCoordinate_normalizesLongitude_acrossIDL() {
        DistanceCalcEarth earth = new DistanceCalcEarth();
        GHPoint p = earth.projectCoordinate(0, 179, 400_000, 90);
        assertTrue(p.getLon() <= 180 && p.getLon() >= -180, "lon not normalized: " + p.getLon());
        assertTrue(p.getLon() < 0, "expected crossing IDL to negative lon, got " + p.getLon());
    }

    @Test
    void calcDistance_overPointList_2Dvs3D() {
        PointList pl2d = new PointList(3, false);
        pl2d.add(48.0, 2.0);
        pl2d.add(48.0005, 2.0005);
        pl2d.add(48.0010, 2.0010);

        PointList pl3d = new PointList(3, true);
        pl3d.add(48.0, 2.0, 0.0);
        pl3d.add(48.0005, 2.0005, 50.0);
        pl3d.add(48.0010, 2.0010, 0.0);

        DistanceCalcEarth earth = new DistanceCalcEarth();
        double d2 = earth.calcDistance(pl2d);
        double d3 = DistanceCalcEarth.calcDistance(pl3d, true);
        assertTrue(d3 >= d2, "3D should be >= 2D: d2=" + d2 + " d3=" + d3);
        assertTrue(d2 > 0 && d3 > 0);
    }
}
