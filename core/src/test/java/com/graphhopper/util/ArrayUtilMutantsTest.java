package com.graphhopper.util;

import com.carrotsearch.hppc.IntArrayList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayUtilMutantsTest {

  @Test
  void invert_roundtrip_onPermutation() {
    IntArrayList p = ArrayUtil.permutation(20, new java.util.Random(7));
    assertTrue(ArrayUtil.isPermutation(p));
    IntArrayList inv = ArrayUtil.invert(p);
    // inv[p[i]] == i
    for (int i = 0; i < p.size(); i++) {
      assertEquals(i, inv.get(p.get(i)));
    }
  }

  @Test
  void subList_edges_emptyAndFull() {
    IntArrayList base = IntArrayList.from(3,1,4,1,5);
    assertEquals(IntArrayList.from(), ArrayUtil.subList(base, 2, 2));
    assertEquals(IntArrayList.from(3,1,4,1,5), ArrayUtil.subList(base, 0, base.size()));
  }

  @Test
  void merge_allDuplicatesRemoved() {
    int[] a = {1,1,2,2,3,3};
    int[] b = {1,2,3,3,3,4};
    assertArrayEquals(new int[]{1,2,3,4}, ArrayUtil.merge(a,b));
  }
}
