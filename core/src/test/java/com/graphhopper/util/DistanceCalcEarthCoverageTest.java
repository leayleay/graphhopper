package com.graphhopper.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DistanceCalcEarthCoverageTest {

  @Test
  void edgeDistance_clampsBeforeA_equalsDistToA() {
    DistanceCalcEarth dc = new DistanceCalcEarth();
    // Segment A(0,0) -> B(0,1). Point R "avant" A, donc projection hors segment côté A.
    double aLat=0, aLon=0, bLat=0, bLon=1;
    double rLat=-0.1, rLon=0.0; // en-dessous de A
    // distance normalisée attendue = dist(R,A)
    double expected = dc.calcNormalizedDist(rLat, rLon, aLat, aLon);
    double actual   = dc.calcNormalizedEdgeDistance(rLat, rLon, aLat, aLon, bLat, bLon);
    assertEquals(expected, actual, 1e-12);
    assertFalse(dc.validEdgeDistance(rLat, rLon, aLat, aLon, bLat, bLon));
  }

  @Test
  void edgeDistance_clampsAfterB_equalsDistToB() {
    DistanceCalcEarth dc = new DistanceCalcEarth();
    // Segment A(0,0) -> B(0,1). Point R "après" B, donc projection hors segment côté B.
    double aLat=0, aLon=0, bLat=0, bLon=1;
    double rLat= 0.1, rLon=1.0; // au-dessus/à droite de B
    double expected = dc.calcNormalizedDist(rLat, rLon, bLat, bLon);
    double actual   = dc.calcNormalizedEdgeDistance(rLat, rLon, aLat, aLon, bLat, bLon);
    assertEquals(expected, actual, 1e-12);
    assertFalse(dc.validEdgeDistance(rLat, rLon, aLat, aLon, bLat, bLon));
  }
}
