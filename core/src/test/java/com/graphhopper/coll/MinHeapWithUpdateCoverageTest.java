package com.graphhopper.coll;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MinHeapWithUpdateCoverageTest {

  @Test
  void update_decreaseKey_movesUp() {
    MinHeapWithUpdate h = new MinHeapWithUpdate(4);
    h.push(2, 20f);
    h.push(1, 10f);
    h.push(3, 30f);
    assertEquals(1, h.peekId());
    h.update(2, 1f); // 2 devient le min
    assertEquals(2, h.peekId());
  }

  @Test
  void clear_thenReuse_isClean() {
    MinHeapWithUpdate h = new MinHeapWithUpdate(3);
    h.push(0, 1f);
    h.push(1, 2f);
    h.clear();
    assertTrue(h.isEmpty());
    // on doit pouvoir réutiliser les ids sans "contains" résiduel
    h.push(1, 0.5f);
    assertEquals(1, h.peekId());
  }
}
