package org.casp.simple.cassandra.model;

import java.time.Instant;
import org.sqlproc.engine.annotation.Pojo;

@Pojo
@SuppressWarnings("all")
public class Dividend {
  private String exchange;
  
  public String getExchange() {
    return this.exchange;
  }
  
  public void setExchange(final String exchange) {
    this.exchange = exchange;
  }
  
  public Dividend _setExchange(final String exchange) {
    this.exchange = exchange;
    return this;
  }
  
  private String symbol;
  
  public String getSymbol() {
    return this.symbol;
  }
  
  public void setSymbol(final String symbol) {
    this.symbol = symbol;
  }
  
  public Dividend _setSymbol(final String symbol) {
    this.symbol = symbol;
    return this;
  }
  
  private Instant date;
  
  public Instant getDate() {
    return this.date;
  }
  
  public void setDate(final Instant date) {
    this.date = date;
  }
  
  public Dividend _setDate(final Instant date) {
    this.date = date;
    return this;
  }
  
  private Double dividend;
  
  public Double getDividend() {
    return this.dividend;
  }
  
  public void setDividend(final Double dividend) {
    this.dividend = dividend;
  }
  
  public Dividend _setDividend(final Double dividend) {
    this.dividend = dividend;
    return this;
  }
  
  public String toStringFull() {
    return "Dividend [exchange=" + exchange + ", symbol=" + symbol + ", date=" + date + ", dividend=" + dividend + "]";
  }
}
