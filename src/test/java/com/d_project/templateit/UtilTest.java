package com.d_project.templateit;

import org.junit.Assert;
import org.junit.Test;

public class UtilTest {

  @Test
  public void test1() {
    Assert.assertEquals("e.f.g", "a.b".replaceAll("a(\\W{1})b", "e$1f$1g") );
  }

  @Test
  public void test2() {
    Assert.assertEquals("e.f.g.h", "a.b.c".replaceAll("a(\\W{1})b(\\W{1})c", "e$1f$1g$1h") );
  }

  @Test
  public void test3() {
    Assert.assertEquals("e.f", "a.b.c".replaceAll("a(\\W{1})b(\\W{1})c", "e$1f") );
  }
}
