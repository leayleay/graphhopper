package com.graphhopper.coll;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MinHeapWithUpdateMutantsTest {

  @Test
  void update_increaseKey_movesDown() {
    MinHeapWithUpdate h = new MinHeapWithUpdate(5);
    h.push(0, 1f);  // min
    h.push(1, 2f);
    h.push(2, 3f);
    assertEquals(0, h.peekId());
    // Augmente la clé du min -> doit descendre et 1 devient min
    h.update(0, 10f);
    assertEquals(1, h.peekId());
    // Poll -> 1, puis 2, puis 0 (devenu grand)
    assertEquals(1, h.poll());
    assertEquals(2, h.poll());
    assertEquals(0, h.poll());
  }

  @Test
  void peekValue_consistentAfterUpdates() {
    MinHeapWithUpdate h = new MinHeapWithUpdate(3);
    h.push(2, 5f);
    h.push(1, 8f);
    h.update(1, 1f); // 1 devient min
    assertEquals(1f, h.peekValue(), 0.0);
    assertEquals(1, h.peekId());
  }
}
