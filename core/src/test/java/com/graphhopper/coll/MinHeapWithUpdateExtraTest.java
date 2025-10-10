package com.graphhopper.coll;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MinHeapWithUpdateExtraTest {
  @Test
  void update_onMissingId_throws() {
    MinHeapWithUpdate h = new MinHeapWithUpdate(3);
    assertThrows(IllegalStateException.class, () -> h.update(1, 1.0f));
  }
  @Test
  void update_decreaseKey_bubblesUpToTop() {
    MinHeapWithUpdate h = new MinHeapWithUpdate(5);
    h.push(2, 5.0f);
    h.push(4, 3.0f);
    h.push(1, 7.0f);
    assertEquals(4, h.peekId());
    h.update(1, 2.5f);
    assertEquals(1, h.peekId());
    assertEquals(1, h.poll());
  }
}
