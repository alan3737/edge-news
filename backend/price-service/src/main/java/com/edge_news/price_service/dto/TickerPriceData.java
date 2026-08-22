package com.edge_news.price_service.dto;

public class TickerPriceData {
  private double c; //current price
  private double d; //change
  private double dp; //percent change
  private double h; //highest price of the day
  private double l; //lowest price of the day
  private double o; //open price for the day
  private double pc; //previous close
  private int t; //unix timestamp

  public TickerPriceData(double c, double d, double dp, double h, double l, double o, double pc, int t) {
    this.c = c;
    this.d = d;
    this.dp = dp;
    this.h = h;
    this.l = l;
    this.o = o;
    this.pc = pc;
    this.t = t;
  }

  public double getC() {
    return c;
  }

  public double getD() {
    return d;
  }

  public double getDp() {
    return dp;
  }

  public double getH() {
    return h;
  }

  public double getL() {
    return l;
  }

  public double getO() {
    return o;
  }

  public double getPc() {
    return pc;
  }

  public int getT() {
    return t;
  }

  public void setC(double c) {
    this.c = c;
  }

  public void setD(double d) {
    this.d = d;
  }

  public void setDp(double dp) {
    this.dp = dp;
  }

  public void setH(double h) {
    this.h = h;
  }

  public void setL(double l) {
    this.l = l;
  }

  public void setO(double o) {
    this.o = o;
  }

  public void setPc(double pc) {
    this.pc = pc;
  }

  public void setT(int t) {
    this.t = t;
  }
}