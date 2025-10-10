package com.graphhopper.util;

import com.graphhopper.util.shapes.GHPoint;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DistanceCalcEarthMutantsTest {

  @Test
  void denormalize_normalize_roundtrip_smallDistance() {
    DistanceCalcEarth dc = new DistanceCalcEarth();
    double d = 37.5; // 37.5 m
    // calcNormalizedDist(double dist) <-> calcDenormalizedDist(double)
    double norm = dc.calcNormalizedDist(d);
    double back = dc.calcDenormalizedDist(norm);
    assertEquals(d, back, 1e-6);
  }

  @Test
  void calcNormalizedDist_symmetry_and_zero() {
    DistanceCalcEarth dc = new DistanceCalcEarth();
    double aLat = 48.8566, aLon = 2.3522;
    double bLat = 52.52,   bLon = 13.405;
    // symétrie
    assertEquals(
        dc.calcNormalizedDist(aLat, aLon, bLat, bLon),
        dc.calcNormalizedDist(bLat, bLon, aLat, aLon),
        0.0
    );
    // distance à soi-même -> 0
    assertEquals(0.0, dc.calcNormalizedDist(aLat, aLon, aLat, aLon), 0.0);
  }

  @Test
  void validEdgeDistance_borderline_zeroDotProducts_false() {
    DistanceCalcEarth dc = new DistanceCalcEarth();
    // Construire un segment A(0,0) -> B(1,0);
    // Choisir R de sorte que (AR·AB)==0 ou (RB·AB)==0 => false attendu
    double aLat=0, aLon=0, bLat=1, bLon=0;

    // R aligné à A (même point projeté) : AR·AB == 0
    double rLat1 = 0, rLon1 = 0.000001; // très proche d'A mais hors segment côté Est
    assertFalse(dc.validEdgeDistance(rLat1, rLon1, aLat, aLon, bLat, bLon));

    // R aligné à B (RB·AB == 0)
    double rLat2 = 1, rLon2 = -0.000001; // très proche de B mais côté Ouest
    assertFalse(dc.validEdgeDistance(rLat2, rLon2, aLat, aLon, bLat, bLon));
  }

  @Test
  void calcNormalizedEdgeDistance3D_handlesNaNAndFactorNaNPath() {
    DistanceCalcEarth dc = new DistanceCalcEarth();
    // Si norm vaut 0, factor devient NaN et est ramené à 0 dans l'implémentation -> on couvre la branche
    double dist = dc.calcNormalizedEdgeDistance3D(
        0, 0, 5,    // R
        0, 0, 5,    // A (identique à R)
        0, 0, 5     // B (identique aussi)  => norm==0
    );
    assertEquals(0.0, dc.calcDenormalizedDist(dist), 1e-6);

    // élévation NaN => on retombe sur la version 2D
    double dist2 = dc.calcNormalizedEdgeDistance3D(
        49, 11, Double.NaN,
        49, 11, 0,
        49, 12, 0
    );
    // La distance 3D doit être identique à la 2D ici
    double expected = dc.calcNormalizedEdgeDistance(49, 11, 49, 11, 49, 12);
    assertEquals(expected, dist2, 1e-12);
  }

  @Test
  void projectCoordinate_headingWrapsCorrectly() {
    DistanceCalcEarth dc = new DistanceCalcEarth();
    GHPoint p1 = dc.projectCoordinate(0, 0, 1000, 360); // 360° == 0°
    GHPoint p2 = dc.projectCoordinate(0, 0, 1000,   0);
    assertEquals(p2.getLat(), p1.getLat(), 1e-9);
    assertEquals(p2.getLon(), p1.getLon(), 1e-9);
  }
}
