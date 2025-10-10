package com.graphhopper.util;

import com.carrotsearch.hppc.IntArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArrayUtilExtraTest {
  @Test
  void applyOrder_throwsWhenOrderLongerThanArray() {
    int[] arr = {10, 20};
    int[] order = {0, 1, 2};
    assertThrows(IllegalArgumentException.class, () -> ArrayUtil.applyOrder(arr, order));
  }
  @Test
  void invert_IntArrayList_variant() {
    IntArrayList list = IntArrayList.from(2, 0, 3, 1);
    IntArrayList inv = ArrayUtil.invert(list);
    assertEquals(IntArrayList.from(1, 3, 0, 2), inv);
  }
}
