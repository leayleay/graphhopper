package com.graphhopper.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayUtilCoverageTest {

  @Test
  void calcSortOrder_stableByPrimaryThenSecondaryAscending() {
    int[] primary   = new int[]{2, 2, 2, 3, 1, 1};
    int[] secondary = new int[]{5, 1, 7, 0, 9, 8};
    // use 3-arg overload for int[]:
    int[] order = ArrayUtil.calcSortOrder(primary, secondary, primary.length);
    assertArrayEquals(new int[]{5, 4, 1, 0, 2, 3}, order);
  }

  @Test
  void calcSortOrder_withN_usesPrefixOnly() {
    int[] primary   = new int[]{9, 1, 1, 0, 5};
    int[] secondary = new int[]{0, 7, 3, 4, 2};
    int[] orderN3 = ArrayUtil.calcSortOrder(primary, secondary, 3);
    assertArrayEquals(new int[]{2, 1, 0}, orderN3);
  }

  @Test
  void applyOrder_roundtripWithCalcSortOrder() {
    int[] arr  = new int[]{40, 10, 20, 30};
    int[] keys = new int[]{ 4,  1,  2,  3};
    int[] order  = ArrayUtil.calcSortOrder(keys, new int[]{0,0,0,0}, keys.length);
    int[] sorted = ArrayUtil.applyOrder(arr, order);
    assertArrayEquals(new int[]{10, 20, 30, 40}, sorted);
  }
}
