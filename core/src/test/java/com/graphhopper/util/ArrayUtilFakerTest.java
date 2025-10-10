package com.graphhopper.util;

import com.carrotsearch.hppc.IntArrayList;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class ArrayUtilFakerTest {
  @Test
  void permutation_isPermutation_onRandomSizeFromFaker() {
    Faker faker = new Faker();
    int n = faker.number().numberBetween(8, 20);
    IntArrayList perm = ArrayUtil.permutation(n, new Random(42));
    assertEquals(n, perm.size());
    assertTrue(ArrayUtil.isPermutation(perm));
  }
}
