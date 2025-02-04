package com.jyujyu.dayonetest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyCalculatorApplication {

  public static void main(String[] args) {
    MyCalculator myCalculator = new MyCalculator();

    myCalculator.add(10.0);
    myCalculator.minus(2.0);
    myCalculator.multiply(2.0);

    myCalculator.divide(2.0);

    log.info(myCalculator.getResult().toString());
  }
}
